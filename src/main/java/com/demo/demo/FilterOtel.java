package com.demo.demo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebFilter
@Component
public class FilterOtel implements Filter {

    static final TelemetryClient telemetryClient = new TelemetryClient();
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            if (httpServletRequest.getRequestURI().startsWith("/app/v1")) {
                logHttpRequestHeaders(httpServletRequest, httpServletResponse);
            }
        }
        chain.doFilter(request, response);
    }
    
    private void logHttpRequestHeaders(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headersMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                int status = response.getStatus();
                String method = request.getMethod();
                headersMap.put(headerName, headerValue);
                headersMap.put("ResultCode", String.valueOf(status));
                headersMap.put("Method", method);
            }
        }
        telemetryClient.trackTrace("http headers", SeverityLevel.Information, headersMap);
        telemetryClient.flush();
    }
}

