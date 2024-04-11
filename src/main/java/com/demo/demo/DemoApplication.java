package com.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@RestController
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@RequestMapping("/") 
	public String home() { 
		String username = "modou";
		logger.info("cs-username=" + username);
		return "Logging Application Demo"; 
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
