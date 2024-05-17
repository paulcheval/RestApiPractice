package com.freeloader.restapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
	
import com.freeloader.restapi.model.UserDetails;
import com.freeloader.restapi.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<UserDetails>> retrieveAllUsers() {
		return ResponseEntity.ok().body(userService.retrieveAllUsers());		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDetails> retreiveUser(@PathVariable("id") long id) {
		ResponseEntity<UserDetails> body;
		try {
			body = ResponseEntity.ok().body(userService.retriveUser(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
		return body;		
	}
	
	@PostMapping("")
	public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails userDetails) {
		UserDetails user = userService.createUser(userDetails);
		//URI uri = URI.create("//api/v1/users/" + user..getId());
		return ResponseEntity.created(null).body(user);
	}
}
