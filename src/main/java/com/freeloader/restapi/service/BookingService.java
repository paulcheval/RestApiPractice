package com.freeloader.restapi.service;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.freeloader.restapi.client.model.Booking;
import com.freeloader.restapi.client.model.BookingClient;
import com.freeloader.restapi.client.model.BookingHealthStatus;
import com.freeloader.restapi.model.AuthToken;
import com.freeloader.restapi.util.ServiceConstants;





@Service
public class BookingService {	

	private AuthService authService;
	private BookingClient bookingClient;
	

	public BookingService(AuthService authService, BookingClient bookingClient) {
		super();
		this.authService = authService;
		this.bookingClient = bookingClient;
		
	}
	
	
	public Booking getBooking(Integer id, AuthToken authToken) {
		try {
			System.out.println("BookingService.getBooking() called for id:" +  id + "token: " + authToken.getToken());
			if (authToken.getToken() == null) {
				System.out.println("BookingService.getBooking() does not have a token");
				String token = authService.createAndStoreCookie();
				authToken.setToken(token);
				System.out.println("BookingService.getBooking() token after null reset: " + token);
				
			}
			return retrieveBooking(id, authToken.getToken());
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
				System.out.println("BookingService.getBooking() got forbidden, updating token");
				String updatedToken = authService.createAndStoreCookie();
				authToken.setToken(updatedToken);
				System.out.println("BookingService.getBooking() updated token: " + updatedToken);
				return retrieveBooking(id, authToken.getToken());
			}

		}
		return null;
	}


	private Booking retrieveBooking(Integer id, String token) {
		return bookingClient.retrieveBooking(id, token);
	}
	
	public BookingHealthStatus getHealthStatus() {
		return bookingClient.retrieveBookingtHealthStatus();          
	}

}
