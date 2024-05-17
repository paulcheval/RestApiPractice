package com.freeloader.restapi.controller;

import static com.freeloader.restapi.util.RestApiTestingConstants.BOOKING_1;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.freeloader.restapi.client.model.BookingHealthStatus;
import com.freeloader.restapi.model.AuthToken;
import com.freeloader.restapi.service.BookingService;
import com.freeloader.restapi.util.RestApiTestingConstants;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
	
		
	@MockBean
	private BookingService bookingService;
	
	@MockBean
	private AuthToken authToken;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void ensureHealthReturnsStatusAsExpected() throws Exception {
		
		when(bookingService.getHealthStatus()).thenReturn(new BookingHealthStatus("UP"));
		mockMvc.perform(get("/api/v1/booking/health"))
			.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"));
	}
	
	@Test void ensureGetBookingReturnsExpectedBooking() throws Exception {
		when(bookingService.getBooking(Mockito.anyInt(), Mockito.any(AuthToken.class))).thenReturn(BOOKING_1);
		mockMvc.perform(get("/api/v1/booking/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bookingid").value(1))
            .andExpect(jsonPath("$.firstname").value(RestApiTestingConstants.FIRST_NAME));
            
	}

}
