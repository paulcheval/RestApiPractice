package com.freeloader.restapi.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeloader.restapi.model.UserDetails;
import com.freeloader.restapi.service.UserService;


@Service
public class KafkaMessageConsumer {

	
	private UserService userService;	
	
	public KafkaMessageConsumer(UserService userService) {
		this.userService = userService;
	}
	
	@KafkaListener(topics = "${message.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
	public void consume(String message) {
		
		System.out.println("Trying to Consumed message: " + message);
		ObjectMapper mapper = new ObjectMapper();
		try {
            UserDetails userDetails = mapper.readValue(message, UserDetails.class);
            System.out.println("Consumed userdetails message: " + userDetails.toString());
            userService.createUser(userDetails);
            
        } catch (Exception e) {
          System.out.println("Error in deserializing message: " + e);
		
        }
	}
	
}
