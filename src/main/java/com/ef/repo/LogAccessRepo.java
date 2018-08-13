package com.ef.repo;

import com.ef.entities.LogAccessEntity;
import com.ef.model.IPRequestCounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogAccessRepo extends JpaRepository<LogAccessEntity, Long> {
    @Query(value = "SELECT la.ip AS ip, COUNT(*) AS threshold FROM LogAccessEntity la WHERE la.logDate >= ?1 AND la.logDate < ?2 GROUP BY la.ip")
    List<IPRequestCounting> findListByParameter(Date startDate, Date endDate, int threshold);
}
