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

	@RequestMapping("/app/v1/test")
	public String appV1(){
		return "app v1 version rc6";
	}

	@RequestMapping("/app/v2")
	public String appV2(){
		return "app v2 version rc6";
	}
	public static void main(String[] args) {
        ApplicationInsights.attach();
        SpringApplication.run(DemoApplication.class, args);
    }
}