package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.microsoft.applicationinsights.attach.ApplicationInsights;


@SpringBootApplication
@RestController
public class DemoApplication {

	@RequestMapping("/app/v1/parcours/utilisateurs")
	public String appV1(){
		return "demo app insights parcours users";
	}

	@RequestMapping("/app/v1/health-check/test")
	public String appV2(){
		return "app insights health check";
	}
	public static void main(String[] args) {
        ApplicationInsights.attach();
        SpringApplication.run(DemoApplication.class, args);
    }
}