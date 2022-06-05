package com.my.restful.RESTfulPractice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.restful.RESTfulPractice.dao.MockBookDAO;

@Service
public class BookService {

	@Autowired
	private MockBookDAO bookDAO;
	
}
