package com.my.restful.test.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.restful.test.bean.Product;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@GetMapping(name = "/products/{id}")
	public Product getProduct(@PathVariable("id") String id) {
		Product product = new Product();
		
		product.setId(id);
		product.setName("瑪莉有隻小狗勾");
		product.setPrice(200);
		
		return product;
	}
	
}
