package com.my.restful.RESTfulPractice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(value = { 
					@PropertySource(value = { "classpath:mail.properties" }),
					@PropertySource(value = {"classpath:application.properties"}) 
				})
public class MailConfig {

	@Value("${mail.host}")
	private String host;
	
	@Value("${mail.port:25}") // use ":" symbol to make default value
	private int port;
	
	@Value("${mail.enable_auth}")
	private boolean enable_auth;
	
	@Value("${mail.enable_starttls}")
	private boolean enable_starttls;
	
	@Value("${mail.protocol}")
	private String protocol;
	
	@Value("${mail.username}")
	private String username;
	
	@Value("${mail.password}")
	private String password;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public boolean isEnable_auth() {
		return enable_auth;
	}

	public boolean isEnable_starttls() {
		return enable_starttls;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setEnable_auth(boolean enable_auth) {
		this.enable_auth = enable_auth;
	}

	public void setEnable_starttls(boolean enable_starttls) {
		this.enable_starttls = enable_starttls;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
