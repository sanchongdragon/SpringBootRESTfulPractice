package com.my.restful.test.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.restful.test.entity.Product;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@GetMapping(name = "/products/{id}")
	public Product getProduct(@PathVariable("id") String id) {
		System.out.printf("getProduct method variable : %s %n", id);
		Product product = new Product();
		
		product.setId(id);
		product.setName("瑪莉有隻小狗勾");
		product.setPrice(400000);
		
		return product;
	}
	
//	@PostMapping(name = "/products/{id}")
//	public Product addProduct(@PathVariable("id") String id) {
//		System.out.printf("addProduct method variable : %s %n", id);
//		Product product = new Product();
//		
//		product.setId(id);
//		product.setName("瑪莉有隻小貓貓");
//		product.setPrice(400000);
//		
//		return product;
//	}
	
}
