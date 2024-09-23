package com.demo.demo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;

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
        
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Map<String, String>  headers = new HashMap<>();
        if  (httpServletRequest.getRequestURI().startsWith("/app/v1")) {
            try{
                headers = logHttpRequestHeaders(httpServletRequest);
                chain.doFilter(request, response);
            }finally{
                headers.put("StatusCode", String.valueOf(httpServletResponse.getStatus()));
                telemetryClient.trackTrace("http headers opentelemetry, Message: "+getCustomMessageOfStatusCode(httpServletResponse.getStatus()), SeverityLevel.Information, headers);
                telemetryClient.flush();
            }
        }
    }
    
    private Map<String, String> logHttpRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headersMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            String method = request.getMethod();
            headersMap.put("Method", method);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                headersMap.put(headerName, headerValue);
            }
        }
       return headersMap;
    }

    private String getCustomMessageOfStatusCode(int statusCode ) {
        switch (statusCode) {
            case HttpServletResponse.SC_OK:
                return "OK";
            case HttpServletResponse.SC_NOT_FOUND:
                return "Not Found";
            case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
                return "Internal Server Error";
            case HttpServletResponse.SC_UNAUTHORIZED:
                return "Unauthorized";
            case HttpServletResponse.SC_FORBIDDEN:
                return "Forbidden";
        
            default:
                return "unknown status";
        }
    }
}

