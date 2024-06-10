package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;

import jakarta.servlet.http.HttpServletRequest;

import com.microsoft.applicationinsights.TelemetryClient;

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
	Map<String, String> mp = new HashMap<>();
	
	@RequestMapping("/header") 
	public Map<String, String> getRequestHeaders(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, String> headersMap = new HashMap<>();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersMap.put(headerName, headerValue);
        }
		String referer = request.getHeader("Referer");
		mp.put("Referer", referer);
		//telemetryClient.trackTrace("Header Request", SeverityLevel.Information, headersMap);
		telemetryClient.trackTrace("Referer",SeverityLevel.Information, mp);
		telemetryClient.trackEvent("EventsCustoms", headersMap, null);
		return headersMap;
	}

	@RequestMapping("/") 
	public String home() { 
		mp.put("Squad", "swap");
		mp.put("Poste", "OPS");
		telemetryClient.trackTrace("User details", SeverityLevel.Information, mp);
		return "Logging Application Demo V6"; 
	}

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(DemoApplication.class, args);
	}

}
