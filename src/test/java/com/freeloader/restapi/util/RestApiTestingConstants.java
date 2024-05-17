package com.freeloader.restapi.util;

import com.freeloader.restapi.client.model.Booking;
import com.freeloader.restapi.client.model.BookingDates;

public class RestApiTestingConstants {
	
	public static final String TOKEN = "token";
	public static final int BOOKING_ID_1= 1;
	public static final int ROOM_ID_1 = 101;
	public static final String FIRST_NAME = "first-name";
	public static final String LAST_NAME = "last-name";;
	public static final String DEPOST_PAID = "true";
	public static final BookingDates BOOKING_DATE = new BookingDates("2021-12-01", "2021-12-02");
	
	public static final Booking BOOKING_1 = new Booking(
			BOOKING_ID_1, 
			ROOM_ID_1, 
			FIRST_NAME, 
			LAST_NAME, 
			DEPOST_PAID, 
			BOOKING_DATE);

}
