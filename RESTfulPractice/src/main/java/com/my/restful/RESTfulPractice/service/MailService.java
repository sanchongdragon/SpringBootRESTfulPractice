package com.my.restful.RESTfulPractice.service;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.my.restful.RESTfulPractice.config.MailConfig;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.SendMailRequest;

@Service
public class MailService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MailConfig config;
	
	private JavaMailSenderImpl mailSenderImpl;
	
	@PostConstruct
	public void init() {
		mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(config.getHost());
		mailSenderImpl.setPort(config.getPort());
		mailSenderImpl.setUsername(config.getUsername());
		mailSenderImpl.setPassword(config.getPassword());

		Properties props = mailSenderImpl.getJavaMailProperties();
		props.put("mail.smtp.auth", config.isEnable_auth());
		props.put("mail.smtp.starttls.enable", config.isEnable_starttls());
		props.put("mail.transport.protocol", config.getProtocol());
	}
	
	public void sendMail(SendMailRequest request) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(config.getUsername());
		message.setTo(request.getReceivers());
		message.setSubject(request.getSubject());
		message.setText(request.getContent());
		
		try {
			mailSenderImpl.send(message);
		} catch (MailException me) {
			LOG.error(me.getMessage());
		}
		
	}
	
}
