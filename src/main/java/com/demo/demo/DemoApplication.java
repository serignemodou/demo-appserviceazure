package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.microsoft.applicationinsights.attach.ApplicationInsights; 

@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer{

	@RequestMapping("/") 
	public String home() { 
		return "Logging Application Demo V01"; 
	}

	public static void main(String[] args) {
        ApplicationInsights.attach();
        SpringApplication.run(DemoApplication.class, args);
    }
}