package com.my.restful.RESTfulPractice.utils;

import com.my.restful.RESTfulPractice.entity.Product;
import com.my.restful.RESTfulPractice.requestModel.ProductRequest;
import com.my.restful.RESTfulPractice.requestModel.ProductResponse;

public class ProductConveter {

	/**
	 * 轉換request成符合DB的物件
	 * */
	public static Product toProduct(ProductRequest request) {
		Product converted = new Product();
		
		converted.setName(request.getName());
		converted.setPrice(request.getPrice());
		
		return converted;
	}
	
	/**
	 * 轉換db成符合response的物件
	 * */
	public static ProductResponse toResponse(Product databaseData) {
		ProductResponse converted = new ProductResponse();
		converted.setId(databaseData.getId());
		converted.setName(databaseData.getName());
		converted.setPrice(databaseData.getPrice());
		
		return converted;
	}

}
