package com.my.restful.RESTfulPractice.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.restful.RESTfulPractice.entity.Book;
import com.my.restful.RESTfulPractice.entity.Product;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)

public class BookController {

	@GetMapping(path = "/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") String id) {
		System.out.printf("getBook method variable : %s %n", id);
		
		Book book = new Book();
		
		book.setIsbn(id);
		book.setName("瑪莉有隻小狗勾");
		book.setPrice(400000);
		
		return ResponseEntity.ok().body(book);
	}
	
	@PostMapping(path = "/books/{id}")
	public ResponseEntity<Book> addBook(@PathVariable("id") String id) {
		System.out.printf("addBook method variable : %s %n", id);
		Book book = new Book();
		
		book.setIsbn(id);
		book.setName("瑪莉有隻小貓咪");
		book.setPrice(123456);
		
		return ResponseEntity.ok().body(book);
	}
	
}
