package com.my.restful.RESTfulPractice.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.my.restful.RESTfulPractice.rest.controller.queryparameter.SendMailRequest;
import com.my.restful.RESTfulPractice.service.MailService;

@RestController
public class MailController {

	@Autowired
	private MailService mailService;
	
	@GetMapping("/mail")
	public void sendMail(@Valid @RequestBody SendMailRequest request) {
		mailService.sendMail(request);
	}
	
}
