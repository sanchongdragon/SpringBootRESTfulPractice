package com.my.restful.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.my.restful.test.rest.controller.ProductController;

@SpringBootApplication
public class ResTfulPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResTfulPracticeApplication.class, args);
	}

}
