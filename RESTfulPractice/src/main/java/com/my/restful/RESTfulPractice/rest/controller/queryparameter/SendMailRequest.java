package com.my.restful.RESTfulPractice.rest.controller.queryparameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SendMailRequest {
	
	private String content;
	
	@NotNull
	@NotEmpty
	private String receivers;
	private String subject;
	
	public String getContent() {
		return content;
	}
	public String getReceivers() {
		return receivers;
	}
	public String getSubject() {
		return subject;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
