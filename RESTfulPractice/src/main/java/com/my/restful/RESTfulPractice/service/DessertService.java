package com.my.restful.RESTfulPractice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.restful.RESTfulPractice.dao.MockDessertDAO;

@Service
public class DessertService {

	@Autowired
	private MockDessertDAO dessertDAO;
}
