package com.freeloader.restapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.freeloader.restapi.util.ServiceConstants;

@Configuration
public class RestClientConfiguration {

    @Bean(name = "roomRestClient")
    RestClient roomRestClient() {
		return RestClient.builder()
				.baseUrl(ServiceConstants.ROOM_BASE_URL)
				
				.build();
	}

    @Bean(name = "authRestClient")
    RestClient authRestClient() {
		return RestClient.builder()
				.baseUrl(ServiceConstants.AUTH_BASE_URL)
				.build();
	}
    
    @Bean(name = "bookingRestClient")
    RestClient bookingRestClient() {
		return RestClient.builder()
				.baseUrl(ServiceConstants.BOOKING_BASE_URL)
				.build();
	}


}
