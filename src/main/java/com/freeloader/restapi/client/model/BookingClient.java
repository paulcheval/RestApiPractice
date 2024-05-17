package com.freeloader.restapi.client.model;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.freeloader.restapi.util.ServiceConstants;

@Component
public class BookingClient {
	
	private final ApplicationContext context;  
	private RestClient restClient;
	
	
	public BookingClient(ApplicationContext context) {
		super();
		this.context = context;
		this.restClient = (RestClient) context.getBean("bookingRestClient");		
	}
	
	public BookingHealthStatus retrieveBookingtHealthStatus() {
		return restClient.get()
				.uri(ServiceConstants.BOOKING_HELATH_SERVICE_URL)				
                .retrieve()
                .body(BookingHealthStatus.class);              
	}
	
	public Booking retrieveBooking(Integer id, String token) {				
		Booking body = restClient.get()
					.uri(ServiceConstants.BOOKING_SERVICE_URL, id)
					.header("Cookie", "token=" + token)
					.retrieve()
					.body(Booking.class);
		return body;
	}
}
