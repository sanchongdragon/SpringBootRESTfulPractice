package com.my.restful.RESTfulPractice.requestModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductRequest {

	@NotEmpty
	private String name;
	@Min(value = 0)
	@NotNull
	private Integer price;
	
	public String getName() {
		return name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
