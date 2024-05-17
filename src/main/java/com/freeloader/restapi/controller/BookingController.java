package com.freeloader.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freeloader.restapi.client.model.Booking;
import com.freeloader.restapi.client.model.BookingHealthStatus;
import com.freeloader.restapi.model.AuthToken;
import com.freeloader.restapi.service.BookingService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/booking")
public class BookingController {
	
	private final BookingService bookingService;
	private final AuthToken authToken;
	
	public BookingController(BookingService bookingService, AuthToken authToken) {
		this.bookingService = bookingService;
		this.authToken = authToken;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Booking> getRooms(@PathVariable Integer id) {
		System.out.println("BookingController.getRooms() called for id:" +  id);
		return ResponseEntity.ok().body(bookingService.getBooking(id, authToken));
	
	}
	
	@GetMapping("/health")
	public ResponseEntity<BookingHealthStatus> getRoomHealthStatus() {
		BookingHealthStatus healthStatus = bookingService.getHealthStatus();
		return ResponseEntity.ok().body(healthStatus);
	}

}
