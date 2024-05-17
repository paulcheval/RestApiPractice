package com.freeloader.restapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.freeloader.restapi.client.model.AuthHealthStatus;
import com.freeloader.restapi.client.model.AuthValidationRequest;
import com.freeloader.restapi.client.model.LoginRequest;
import com.freeloader.restapi.model.AuthToken;
import com.freeloader.restapi.util.ServiceConstants;

@Service
public class AuthService {
	
	private final ApplicationContext context;  
	private RestClient restClient;
	private AuthToken authToken;
	
	@Value("${authservice.username}")
	private String authUser;
	@Value("${authservice.password}")
	private String authPassword;

	public AuthService(ApplicationContext context, AuthToken authToken) {
		super();
		this.context = context;
		this.restClient = (RestClient) context.getBean("authRestClient");
		this.authToken = authToken;
	}
	
	
	
	public AuthHealthStatus getAuthStatus() {
		return restClient.get()
				.uri(ServiceConstants.AUTH_HELATH_SERVICE_URL)				
                .retrieve()
                .body(AuthHealthStatus.class);
                
	}
	
	public ResponseEntity<Void> performAuthValidation(AuthValidationRequest request) {
		System.out.println("AuthService.performAuthValidation() called" );
		try {
			 return restClient.post()
					.uri(ServiceConstants.AUTH_SERVICE_VALIDATE_URL)	
					.contentType(MediaType.APPLICATION_JSON)				
					.body(request)
					.retrieve()
					.toBodilessEntity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
		
	}

	public ResponseEntity<Void> createNewToken(LoginRequest request) {
		System.out.println("AuthService.createNewToken() called" );
		return restClient.post()
				.uri(ServiceConstants.AUTH_ADD_TOKENURL)
				.contentType(MediaType.APPLICATION_JSON)
				.body(request)
				.retrieve()
				.toBodilessEntity();

	}

	public String createAndStoreCookie() {
		System.out.println("AuthService.createAndStoreCookie() called" );
		return extractTokenFromCookie(createNewToken(new LoginRequest(authUser, authPassword)));
	}

	
	private String extractTokenFromCookie(ResponseEntity<Void> newToken) {
		Optional<String> extractedToken = Stream.of(extractDetailsFromCookieResponse(newToken).split(";"))
				.filter(x->x.contains("token="))
				.map(x->x.split("=")[1])
				.findFirst();
		if (extractedToken.isPresent()) {			
			return extractedToken.get();
		}
		throw new RuntimeException("No token found in response");

	}

	private String extractDetailsFromCookieResponse(ResponseEntity<Void> newToken) {
		return newToken.getHeaders().get("set-cookie").stream()
				.filter(cookie -> cookie.startsWith("token="))
				.findFirst().orElse("");
	}

}
