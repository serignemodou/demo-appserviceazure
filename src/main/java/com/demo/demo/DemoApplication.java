package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;

import jakarta.servlet.http.HttpServletRequest;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
@RestController
public class DemoApplication {


	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	private static final TelemetryClient telemetryClient = new TelemetryClient();
	RequestTelemetry requestTelemetry = new RequestTelemetry();
	Map<String, String> mp = new HashMap<>();

	@RequestMapping("/") 
	public String home() { 
		// Set custom properties
		requestTelemetry.getProperties().put("customProperty1", "value1");
		requestTelemetry.getProperties().put("customProperty2", "value2");
		// Set custom others
		requestTelemetry.setSuccess(true);
		requestTelemetry.setName("SWAP-TEAMS");
		telemetryClient.trackRequest(requestTelemetry);
		return "Logging Application Demo V10"; 
	}

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(DemoApplication.class, args);
	}

}
