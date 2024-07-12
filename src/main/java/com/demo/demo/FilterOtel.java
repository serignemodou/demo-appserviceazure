package com.demo.demo;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.web.bind.annotation.RequestMapping;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class FilterOtel implements Filter {

    static final TelemetryClient telemetryClient = new TelemetryClient();
    RequestTelemetry requestTelemetry = new RequestTelemetry();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            logHttpRequestHeaders(httpServletRequest);
        }
        chain.doFilter(request, response);
    }
    
    private void logHttpRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                System.out.println("Header: " + headerName + " = " + headerValue);
                requestTelemetry.getProperties().put(headerName, headerValue);
            }
            System.out.println("Application version v01");
        }
    }
}
