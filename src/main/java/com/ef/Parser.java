package com.ef;

import com.ef.service.LogParserService;
import com.ef.service.QueryLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Parser implements CommandLineRunner {
	private static Logger LOG = LoggerFactory
			.getLogger(Parser.class);

	@Autowired
	LogParserService logParserService;

	@Autowired
	QueryLogService queryLogService;

	public static void main(String[] args) {
		SpringApplication.run(Parser.class, args);
	}

	@Override
	public void run(final String... args) throws Exception {
		if ( args.length == 0 || ("--help".equalsIgnoreCase(args[0]) && "-h".equalsIgnoreCase(args[0]))) {
			System.out.println("java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=[hourly|daily] --threshold=<Integer>");
			return;
		}

		String accessLogFile = "";
		String startDateStr = "";
		String duration = "";
		String thresholdStr = "";

		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--accesslog=")) {
				accessLogFile = args[i].split("=")[1];

				if (StringUtils.isEmpty(accessLogFile)) {
					System.out.println("java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=[hourly|daily] --threshold=<Integer>");
					return;
				}
			} else if (args[i].startsWith("--startDate=")) {
				startDateStr = args[i].split("=")[1];

				if (StringUtils.isEmpty(startDateStr)) {
					System.out.println("java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=[hourly|daily] --threshold=<Integer>");
					return;
				}
			} else if(args[i].startsWith("--duration=")) {
				duration = args[i].split("=")[1];

				if (StringUtils.isEmpty(duration)) {
					System.out.println("java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=[hourly|daily] --threshold=<Integer>");
					return;
				}

				if (!Arrays.asList("hourly", "daily").contains(duration)) {
					System.out.println("Invalid parameter duration");
					return;
				}
			} else if(args[i].startsWith("--threshold=")) {
				thresholdStr = args[i].split("=")[1];

				if (StringUtils.isEmpty(thresholdStr)) {
					System.out.println("java -cp parser.jar com.ef.Parser --accesslog=/path/to/file --startDate=yyyy-MM-dd.HH:mm:ss --duration=[hourly|daily] --threshold=<Integer>");
					return;
				}
			}
		}

		final LocalDateTime localDateTime = LocalDateTime.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
		final Date startDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		int threshold = Integer.parseInt(thresholdStr);

		if ("hourly".equals(duration)) {
			threshold = threshold > 200 ? 200 : threshold;
		} else {
			threshold = threshold > 500 ? 500 : threshold;
		}

		logParserService.parseLog(accessLogFile);

		final List<String> ips = queryLogService.findIPsRequestShouldBlock(startDate, duration, threshold);

		System.out.println(ips.stream().collect(Collectors.joining(", ")));
	}
}
