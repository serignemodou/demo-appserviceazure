package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.microsoft.applicationinsights.attach.ApplicationInsights;


import jakarta.servlet.http.HttpServletRequest; 


@SpringBootApplication
@RestController
public class DemoApplication {
	@RequestMapping("/test") 
	public String getRequestHeaders(HttpServletRequest request) {
		return "Logging Application Demo V2"; 
	}

	@RequestMapping("/demo/v1")
	public String showInformation(){
		return "Hello demo v1";
	}

	@RequestMapping("/demo")
	public String showV2(){
		return "Hello demo v2";
	}
	public static void main(String[] args) {
        ApplicationInsights.attach();
        SpringApplication.run(DemoApplication.class, args);
    }
}