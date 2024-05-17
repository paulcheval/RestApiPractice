package com.freeloader.restapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freeloader.restapi.client.model.AuthHealthStatus;
import com.freeloader.restapi.client.model.AuthValidationRequest;
import com.freeloader.restapi.client.model.LoginRequest;
import com.freeloader.restapi.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@GetMapping("/health")
	public ResponseEntity<AuthHealthStatus> getAuthHealthStatus() {
		return ResponseEntity.ok().body(authService.getAuthStatus());
	}

	@PostMapping("/validate")
	public ResponseEntity<Void> validate(@RequestBody AuthValidationRequest request) {
		System.out.println("AuthController.validate() called");
		System.out.println("Request: " + request.toString());
		ResponseEntity<Void> authValidateion = authService.performAuthValidation(request);		
		System.out.println("Response: " + authValidateion.getStatusCode());
		return authValidateion;
	}

	@PostMapping("/token")
	public ResponseEntity<String> createNewToken(@RequestBody LoginRequest request) {
		try {
			String determineCookieValue = determineCookieValue(
					authService.createNewToken(request)
						.getHeaders()
							.get("set-cookie"));
			if (!determineCookieValue.isEmpty()) {
				return ResponseEntity.ok().body(determineCookieValue);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

	private String determineCookieValue(List<String> setCookie) {
		String tokenString = setCookie.stream().filter(cookie -> cookie.startsWith("token=")).findFirst().orElse("");
		List<String> tokens = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(tokenString, ";");
		while (st.hasMoreTokens()) {
			tokens.add(st.nextToken());
		}
		for (String token : tokens) {
			if (token.startsWith("token=")) {
				String[] tokenParts = token.split("=");
				if (tokenParts.length == 2 && tokenParts[0].equals("token")) {
					return tokenParts[1];
				}
			}
		}
		return "";
	}

}
