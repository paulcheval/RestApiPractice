package com.freeloader.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestApiPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiPracticeApplication.class, args);
	}

}
