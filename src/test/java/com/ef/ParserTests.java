package com.ef;

import com.ef.entities.IPBlockEntity;
import com.ef.entities.LogAccessEntity;
import com.ef.repo.IPBlockRepo;
import com.ef.repo.LogAccessRepo;
import com.ef.service.BlockIPService;
import com.ef.service.LogParserService;
import com.ef.service.QueryLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, BlockIPService.class, QueryLogService.class, LogParserService.class})
public class ParserTests {
	@Autowired
	BlockIPService blockIPService;

	@Autowired
	LogParserService logParserService;

	@Autowired
	QueryLogService queryLogService;

	@Autowired
	IPBlockRepo ipBlockRepo;

	@Autowired
	LogAccessRepo logAccessRepo;

	@Test
	public void testBlockService_expectSaveSuccess() {
		blockIPService.blockIP("192.168.80.215", "daily", 100);

		List<IPBlockEntity> ipBlockEntities = ipBlockRepo.findAll();

		Assert.assertEquals(1, ipBlockEntities.size());

		Assert.assertEquals("192.168.80.215", ipBlockEntities.get(0).getIp());

		Assert.assertEquals("192.168.80.215 is block because it's request exceed 100 requests on a day", ipBlockEntities.get(0).getComment());

		ipBlockRepo.deleteAll();
	}

	@Test
	public void testLogParserService_expectReadFileAndParseSuccess() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		String exampleFile = classLoader.getResource("file/example.log").getPath();

		logParserService.parseLog(exampleFile);

		List<LogAccessEntity> logAccessEntities = logAccessRepo.findAll();

		Assert.assertEquals(5, logAccessEntities.size());

		logAccessRepo.deleteAll();
	}

	@Test
	public void testQueryLogService_expectGetLogAccessSuccess() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		String exampleFile = classLoader.getResource("file/example.log").getPath();

		logParserService.parseLog(exampleFile);

		List<String> ips = queryLogService.findIPsRequestShouldBlock(Date.from(LocalDateTime.parse("2017-01-01 00:00:00.000", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")).atZone(ZoneId.systemDefault()).toInstant()), "daily", 1);

		Assert.assertEquals(1, ips.size());

		logAccessRepo.deleteAll();
	}
}
