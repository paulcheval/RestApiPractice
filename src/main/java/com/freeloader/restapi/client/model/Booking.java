package com.freeloader.restapi.client.model;

public record Booking(
		int bookingid,
		int roomid,
		String firstname,
		String lastname,
		String depositpaid,
		BookingDates bookingdates) {

}
