package com.demo.demo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest; 


@SpringBootApplication
@RestController
public class DemoApplication {

	static final TelemetryClient telemetryClient = new TelemetryClient();
    RequestTelemetry requestTelemetry = new RequestTelemetry();
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);


	/* 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            logHttpRequestHeaders(httpServletRequest);
        }
        chain.doFilter(request, response);
		logger.info("Do filer called");
		
    }
	*/
    
    private void logHttpRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                System.out.println("Header: " + headerName + " = " + headerValue);
                requestTelemetry.getProperties().put(headerName, headerValue);
				telemetryClient.trackRequest(requestTelemetry);
            }
            System.out.println("Application version v01");
			logger.info("Application version v01");
        }
    }
	@RequestMapping("/") 
	public String getRequestHeaders(HttpServletRequest request) {
		logHttpRequestHeaders(request);
		return "Logging Application Demo V03"; 
	}

	public static void main(String[] args) {
        ApplicationInsights.attach();
        SpringApplication.run(DemoApplication.class, args);
    }
}