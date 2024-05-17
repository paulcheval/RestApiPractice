package com.freeloader.restapi.model;

import org.springframework.stereotype.Component;

@Component
public class AuthToken {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AuthToken [token=" + token + "]";
	}
}
