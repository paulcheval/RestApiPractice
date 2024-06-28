package com.freeloader.restapi.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class ControllerException {
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Object> exception(UserNotFoundException exception) {
		return new ResponseEntity<Object>("User Not Found", HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(value = ConstraintViolationException.class)
	    @ResponseBody
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public Object handleRequestValidationException(Exception ex, HttpServletRequest request) {
	        Map<String, Object> responseBody = new LinkedHashMap<>();
	         
	        responseBody.put("timestamp", new Date());
	        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
	        responseBody.put("error", ex.getMessage());
	        responseBody.put("path", request.getServletPath());
	         
	        return responseBody;
	    }

}
