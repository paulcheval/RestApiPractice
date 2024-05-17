package com.freeloader.restapi.service;

import static com.freeloader.restapi.util.RestApiTestingConstants.BOOKING_1;
import static com.freeloader.restapi.util.RestApiTestingConstants.TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import com.freeloader.restapi.client.model.Booking;
import com.freeloader.restapi.client.model.BookingClient;
import com.freeloader.restapi.client.model.BookingDates;
import com.freeloader.restapi.client.model.BookingHealthStatus;
import com.freeloader.restapi.model.AuthToken;
import com.freeloader.restapi.util.RestApiTestingConstants;

@SpringBootTest
class BookingServiceTest {
	
	@MockBean
	private BookingClient bookingClient;
	
	@MockBean
	private AuthService authService;
	
	
	@Autowired
	private BookingService bookingService;
	
	

	@Test
	void ensureHealthStatusReturnsAsExpected() {
		when(bookingClient.retrieveBookingtHealthStatus())
			.thenReturn(new BookingHealthStatus("UP"));
		
		BookingHealthStatus healthStatus = bookingService.getHealthStatus();
		if (healthStatus == null || !"UP".equals(healthStatus.getStatus())) {
			fail("Health status is not as expected");
		}
		when(bookingClient.retrieveBookingtHealthStatus())
			.thenReturn(new BookingHealthStatus("DOWN"));
		
		 healthStatus = bookingService.getHealthStatus();
		 if (healthStatus == null || !"DOWN".equals(healthStatus.getStatus())) {
				fail("Health status is not as expected");
			}
	}
	
	@Test
	void ensureBookingClientCalledWhenTokenIsNUll() {
		
		when(authService.createAndStoreCookie()).thenReturn(TOKEN);
		when(bookingClient.retrieveBooking(1, TOKEN)).thenReturn(BOOKING_1);
		
		Booking booking = bookingService.getBooking(1, new AuthToken());
		Mockito.verify(bookingClient, Mockito.times(1)).retrieveBooking(Mockito.anyInt(), Mockito.nullable(String.class));
		Mockito.verify(authService, Mockito.times(1)).createAndStoreCookie();
		assertEquals(BOOKING_1, booking);
		
	}
	
	@Test
	void ensureBookingClientCalledWhenTokenIsPopulateNoFailure() {
		
		when(authService.createAndStoreCookie()).thenReturn(TOKEN);
		when(bookingClient.retrieveBooking(1, TOKEN)).thenReturn(BOOKING_1);
		AuthToken authToken = new AuthToken();
		authToken.setToken(TOKEN);
		
		Booking booking = bookingService.getBooking(1, authToken);
		Mockito.verify(bookingClient, Mockito.times(1)).retrieveBooking(Mockito.anyInt(), Mockito.nullable(String.class));
		Mockito.verify(authService, Mockito.times(0)).createAndStoreCookie();
		assertEquals(BOOKING_1, booking);
		
	}
	
	@Test
	void ensureBookingClientCalledWhenTokenIsPopulateWIthFailureOnRemoteCall() {		
		when(authService.createAndStoreCookie()).thenReturn(TOKEN);
		when(bookingClient.retrieveBooking(1, TOKEN)).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN)).thenReturn(BOOKING_1);
		AuthToken authToken = new AuthToken();
		authToken.setToken(TOKEN);
		
		Booking booking = bookingService.getBooking(1, authToken);
		Mockito.verify(bookingClient, Mockito.times(2)).retrieveBooking(Mockito.anyInt(), Mockito.nullable(String.class));
		Mockito.verify(authService, Mockito.times(1)).createAndStoreCookie();
		assertEquals(BOOKING_1, booking);
		
	}

}
