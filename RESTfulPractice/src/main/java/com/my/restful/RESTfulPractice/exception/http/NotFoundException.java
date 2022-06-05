package com.my.restful.RESTfulPractice.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404
public class NotFoundException extends RuntimeException{

	public NotFoundException (String msg) {
		super(msg);
	}
	
}
