package com.ef.service;

import com.ef.entities.LogAccessEntity;
import com.ef.repo.LogAccessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class LogParserService {
    @Autowired
    private LogAccessRepo logAccessRepo;

    @Transactional(rollbackFor = Exception.class)
    public void parseLog(final String logPath) throws IOException {
        try (final BufferedReader br = Files.newBufferedReader(Paths.get(logPath))) {
            br.lines().filter(log -> !"".equals(log)).forEach(log -> this.processAccessLog(log));
        } catch (final IOException e) {
            throw e;
        }
    }

    private void processAccessLog(final String log) {
        final String[] logArr = log.split("\\|");
        final String logDateStr = logArr[0];
        final String logIP = logArr[1];
        final String request = logArr[2];
        final String statusStr = logArr[3];
        final String agent = logArr[4];

        final LocalDateTime logDate = LocalDateTime.parse(logDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        final Integer status = Integer.parseInt(statusStr);

        logAccessRepo.save(LogAccessEntity.create()
            .withLogDate(Date.from(logDate.atZone(ZoneId.systemDefault()).toInstant()))
            .withIp(logIP)
            .withRequest(request)
            .withStatus(status)
            .withUserAgent(agent)
        );
    }
}
