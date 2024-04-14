package com.fsd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
 
public class ExceptionController {
 
	@ExceptionHandler(value=Exception.class)
 
	public ResponseEntity<Object> exceptionMethod(Exception ex){
		System.out.println("Error Controller called");
 
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
 
	}
	
 
	
}