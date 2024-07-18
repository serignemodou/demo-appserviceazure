package com.demo.demo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;

@WebFilter(urlPatterns =  "/*")
@Component
public class FilterOtel implements Filter {

    static final TelemetryClient telemetryClient = new TelemetryClient();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if needed
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            logHttpRequestHeaders(httpServletRequest);
        }
        chain.doFilter(request, response);
    }
    
    private void logHttpRequestHeaders(HttpServletRequest request) {
        RequestTelemetry requestTelemetry = new RequestTelemetry();
        

        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headersMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                System.out.println("Header" + headerName + " = " + headerValue);
                System.out.println("????+++!!!!" + requestTelemetry.getProperties());
                requestTelemetry.getProperties().put("_MS.ProcessedByMetricExtractors", "true");
                requestTelemetry.getProperties().put(headerName, headerValue);
             headersMap.put(headerName, headerValue);
            }
        }
       // telemetryClient.trackTrace("users details", SeverityLevel.Information, headersMap);
        telemetryClient.trackRequest(requestTelemetry);
      //  telemetryClient.flush();
    }
}

