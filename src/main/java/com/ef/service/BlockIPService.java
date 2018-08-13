package com.ef.service;

import com.ef.entities.IPBlockEntity;
import com.ef.repo.IPBlockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockIPService {
    @Autowired
    private IPBlockRepo ipBlockRepo;

    @Transactional(rollbackFor = Exception.class)
    public void blockIP(final String ip, final String duration, final int threshold) {
        ipBlockRepo.save(IPBlockEntity.create()
                .withIp(ip)
                .withComment(buildComment(ip, duration, threshold)));
    }

    private String buildComment(final String ip, final String duration, final int threshold) {
        return ip + " is block because it's request exceed " + threshold + " requests on " + ("hourly".equals(duration) ? "an hour" : "a day");
    }
}
