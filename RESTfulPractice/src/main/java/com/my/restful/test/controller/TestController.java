package com.my.restful.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.my.restful.test.entity.Product;

@Controller
public class TestController {
	
	@GetMapping(name = "/test/{id}")
	public Product getProduct(@PathVariable("id") String id) {
		Product p = new Product();
		p.setId(id);
		p.setName("Mary see the futrue");
		p.setPrice(2000);
		return p;
	}
	
}
