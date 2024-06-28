package com.freeloader.restapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freeloader.restapi.exception.UserNotFoundException;
import com.freeloader.restapi.model.UserDetails;
import com.freeloader.restapi.service.UserService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Value("${number.page.min}")
	private long minNumberPerPage;
	
	@Value("${number.page.max}")
	private long maxNumberPerPage;
	
	@GetMapping("")
	public ResponseEntity<List<UserDetails>> retrieveAllUsers(
			@RequestParam(value = "page", required = false, defaultValue = "0") 
				@Min(value = 0, message = "Size must be greater than 0") 
					int page,
            @RequestParam(value = "size", required = false) 
					Optional<Integer> size) {
		Integer pageSize = size.orElse(10);
		validatePageSize(pageSize);
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		return ResponseEntity.ok().body(userService.retrieveAllUsers(pageable));		
	}
	
	private void validatePageSize(Integer pageSize) {
		if (pageSize > maxNumberPerPage) {
			throw new ConstraintViolationException("Size must be less than 11", null);
		}
		if (pageSize < minNumberPerPage) {
			throw new ConstraintViolationException("Size must be greater than 3", null);
		}
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDetails> retreiveUser(@PathVariable("id") long id) {
		ResponseEntity<UserDetails> body;
		try {
			body = ResponseEntity.ok().body(userService.retriveUser(id));
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			return  ResponseEntity.notFound().build();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.badRequest().build();
		}
		return body;		
	}
	
	@PostMapping("")
	public ResponseEntity<String> createUser(@RequestBody UserDetails userDetails) {
		long userId = userService.createUser(userDetails);
		URI uri = URI.create("/api/v1/users/" + userId);
		return ResponseEntity.status(HttpStatus.CREATED).location(uri).build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<UserDetails>> searchUsers(@RequestParam(value="phoneNumber", required = false) String phoneNumber,
														@RequestParam(value="email", required = false) String email,
														@RequestParam(value="firstName", required = false) String firstName,
														@RequestParam(value="lastName", required = false) String lastName,
														@RequestParam(value="streetAddress", required = false) String streetAddress,
														@RequestParam(value="city", required = false) String city) {
		SearchParameters searchParameters = new SearchParameters(phoneNumber, email, firstName, lastName, streetAddress, city);
		return ResponseEntity.ok().body(userService.findUsersByPhoneNumber(searchParameters));
	}
}
