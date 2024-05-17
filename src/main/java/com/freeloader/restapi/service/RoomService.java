package com.freeloader.restapi.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.freeloader.restapi.client.model.RoomHealthStatus;
import com.freeloader.restapi.model.Room;
import com.freeloader.restapi.model.Rooms;
import com.freeloader.restapi.util.ServiceConstants;



@Service
public class RoomService {	

	private final ApplicationContext context;  
	private RestClient restClient;

	public RoomService(ApplicationContext context) {
		super();
		this.context = context;
		this.restClient = (RestClient) context.getBean("roomRestClient");
	}
	
	
	public List<Room> getRooms() {
		return restClient.get()
					.uri("/")
					.retrieve()
					.body(Rooms.class)
				.rooms();
	}
	
	public RoomHealthStatus getHealthStatus() {
		return restClient.get()
				.uri(ServiceConstants.ROOM_HEALTH_SERVICE_URL)				
                .retrieve()
                .body(RoomHealthStatus.class);              
	}

}
