package com.my.restful.RESTfulPractice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.restful.RESTfulPractice.dao.MockProductDAO;
import com.my.restful.RESTfulPractice.entity.Product;
import com.my.restful.RESTfulPractice.exception.http.NotFoundException;
import com.my.restful.RESTfulPractice.exception.http.UnprocessableEntityException;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter;

@Service
public class ProductService {
	
	
	@Autowired // 透過Autowired 來達成dependency injection
	private MockProductDAO productDAO;
	
	/**
	 * 建立產品
	 * */
	public Product createProduct(Product product) {
		boolean isDuplicate = productDAO.findId(product.getId()).isPresent();
		
		if(isDuplicate)
			throw new UnprocessableEntityException("The id of the product is duplicated.");
		
		Product p = new Product();
		p.setId(product.getId());
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		
		return productDAO.insertProduct(p);
	}
	/**
	 * 取得產品
	 * */
	public Product getProduct(String id) {
		return productDAO.findId(id).orElseThrow(() -> new NotFoundException("can not find this id"));
	}
	
	/**
	 * 更換產品
	 * */
	public Product replaceProduct(String id, Product request) {
		Product product = getProduct(id);
		return productDAO.replaceProduct(product.getId(), request);
	}
	/**
	 * 刪除產品
	 * */
	public boolean deleteProduct(String id) {
		Product product = getProduct(id);
		return productDAO.deleteProduct(product.getId());
	}
	
	public List<Product> getProduct(ProductQueryParameter parameter){
		return productDAO.findProductList(parameter);
	}
	
}
