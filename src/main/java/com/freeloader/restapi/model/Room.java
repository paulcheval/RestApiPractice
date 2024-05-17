package com.freeloader.restapi.model;

import java.util.List;

public record Room(
		int roomid,
		String roomName,
		String type,
		boolean accessible,
		String image,
		String description,
		List<String> features,
		int roomPrice ) {

}
