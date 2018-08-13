package com.ef.service;

import com.ef.entities.LogAccessEntity;
import com.ef.model.IPRequestCounting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryLogService {
    private final long HOURLY = 60 * 60 * 1000;
    private final long DAILY = 24 * 60 * 60 * 1000;

    @Autowired
    EntityManager em;

    @Autowired
    BlockIPService blockIPService;

    public List<String> findIPsRequestShouldBlock(final Date startDate, final String duration, int threshold) {
        long durationAdd = "hourly".equals(duration) ? HOURLY : DAILY;

        final Date endDate = new Date(startDate.getTime() + durationAdd);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<IPRequestCounting> criteriaQuery = cb.createQuery(IPRequestCounting.class);
        Root<LogAccessEntity> root = criteriaQuery.from(LogAccessEntity.class);
        criteriaQuery.where(cb.greaterThanOrEqualTo(root.<Date>get("logDate"), startDate), cb.lessThanOrEqualTo(root.<Date>get("logDate"), endDate));

        Expression<String> groupByExp = root.get("ip").as(String.class);

        Expression<Long> countExp = cb.count(groupByExp);

        CriteriaQuery<IPRequestCounting> select = criteriaQuery.multiselect(groupByExp, countExp);

        criteriaQuery.groupBy(groupByExp);
        criteriaQuery.having(cb.gt(cb.count(root), threshold));

        TypedQuery<IPRequestCounting> query = em.createQuery(select);
        List<IPRequestCounting> ipRequestCountingList = query.getResultList();

        final List<String> ips = ipRequestCountingList.stream().map(IPRequestCounting::getIp).collect(Collectors.toList());

        ips.forEach(ip -> blockIPService.blockIP(ip, duration, threshold));

        return ips;
    }
}
