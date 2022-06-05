package com.my.restful.RESTfulPractice.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
public class UnprocessableEntityException extends RuntimeException{

	public UnprocessableEntityException(String msg) {
		super(msg);
	}
	
}
