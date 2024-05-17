package com.freeloader.restapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freeloader.restapi.client.model.RoomHealthStatus;
import com.freeloader.restapi.model.Room;
import com.freeloader.restapi.service.RoomService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/rooms")
public class RoomController {
	
	private final RoomService roomService;
	
	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}
	
	@GetMapping
	public ResponseEntity<List<Room>> getRooms() {
		return ResponseEntity.ok().body(roomService.getRooms());
	
	}
	@GetMapping("/health")
	public ResponseEntity<RoomHealthStatus> getRoomHealthStatus() {
		return ResponseEntity.ok().body(roomService.getHealthStatus());
	}

}
