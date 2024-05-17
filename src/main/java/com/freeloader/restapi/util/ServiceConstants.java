package com.freeloader.restapi.util;

public interface ServiceConstants {
	
	public static final String AUTH_BASE_URL = "https://automationintesting.online/auth";
	public static final String AUTH_HELATH_SERVICE_URL = "/actuator/health";
	public static final String AUTH_SERVICE_VALIDATE_URL = "/validate";
	public static final String AUTH_ADD_TOKENURL = "/login";
	
	public static final String ROOM_BASE_URL = "https://automationintesting.online/room";
	//public static final String ROOM_BASE_URL = "https://jsonplaceholder.typicode.com";
	public static final String ROOM_URL = "/";
	//public static final String ROOM_URL = "/posts";
	public static final String ROOM_HEALTH_SERVICE_URL = "/actuator/health";
	
	
	public static final String BOOKING_BASE_URL = "https://automationintesting.online/booking";
	public static final String BOOKING_HELATH_SERVICE_URL = "/actuator/health";
	public static final String BOOKING_SERVICE_URL = "/{id}";


}
