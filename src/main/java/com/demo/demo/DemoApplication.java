package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import com.microsoft.applicationinsights.TelemetryClient;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@SpringBootApplication
@RestController
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	private static final TelemetryClient telemetryClient = new TelemetryClient();
	Map<String, String> mp = new HashMap<>();
	

	@RequestMapping("/") 
	public String home() { 
		String username = "modou";
		mp.put("Squad", "swap");
		mp.put("Poste", "OPS");
		logger.info("cs-username=" + username);
		telemetryClient.trackTrace("User details", SeverityLevel.Information, mp);
		return "Logging Application Demo V3"; 
	}

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(DemoApplication.class, args);
	}

}
