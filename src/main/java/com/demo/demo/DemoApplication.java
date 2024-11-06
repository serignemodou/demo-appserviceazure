package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.microsoft.applicationinsights.attach.ApplicationInsights;


@SpringBootApplication
@RestController
public class DemoApplication {

	@RequestMapping("/app/v1/postetravail/gestionnaire/tenants/historique")
	public String appV1(){
		return "tenant fouth path url, v+8";
	}

	@RequestMapping("/app/v1/health-check")
	public String appV2(){
		return "tenant just path after the domain v+8";
	}
	public static void main(String[] args) {
        ApplicationInsights.attach();
        SpringApplication.run(DemoApplication.class, args);
    }
}