package com.my.restful.RESTfulPractice.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.restful.RESTfulPractice.Interface.ProductRepository;
import com.my.restful.RESTfulPractice.dao.MockProductDAO;
import com.my.restful.RESTfulPractice.entity.Product;
import com.my.restful.RESTfulPractice.exception.http.NotFoundException;
import com.my.restful.RESTfulPractice.exception.http.UnprocessableEntityException;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter2;

@Service
public class ProductService {
	
	@Autowired // 透過Autowired 來達成dependency injection
	private MockProductDAO productDAO;
	
	@Autowired
	private ProductRepository productRepository;
	/**
	 * 建立產品
	 * */
	public Product createProduct(Product product) {
//		boolean isDuplicate = productRepository.findById(product.getId()).isPresent();
//		
//		if(isDuplicate)
//			throw new UnprocessableEntityException("The id of the product is duplicated.");
		
		Product p = new Product();
//		p.setId(product.getId());
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		
		return productRepository.insert(p);
	}
	/**
	 * 取得產品
	 * */
	public Product getProduct(String id) {
		return productRepository.findById(id).orElseThrow(() -> new NotFoundException("can not find this id"));
	}
	
	/**
	 * 更換產品
	 * */
	public Product replaceProduct(String id, Product request) {
		Product product = getProduct(id);
		
		Product newProduct = new Product();
		newProduct.setId(product.getId());
		newProduct.setName(request.getName());
		newProduct.setPrice(request.getPrice());
		
		return productRepository.save(newProduct);
	}
	/**
	 * 刪除產品
	 * */
	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}
	
	public List<Product> getProduct(ProductQueryParameter2 parameter){
		String keyword = Optional.ofNullable(parameter.getKeyword()).orElse("");
		int priceFrom = Optional.ofNullable(parameter.getPriceFrom()).orElse(0);
		int priceTo = Optional.ofNullable(parameter.getPriceTo()).orElse(Integer.MAX_VALUE);
		
		Sort sort = genSortStrategy(parameter.getOrderBy(), parameter.getSortRule());
		return productRepository.findByPriceBetweenAndNameLikeIgnoreCase(priceFrom, priceTo, keyword);
	}
	
	/**
	 * 透過原生 mongoDB 的 query 語法去撈出價格 >= priceFrom, <= priceTo 中間的產品
	 * */
	public List<Product> getProduct(int priceFrom, int priceTo){
		List<Product> ll = productRepository.findByPriceBetween(priceFrom, priceTo);
		ll.forEach(a -> 
			System.out.println(a.getName() + " delete success : "+productRepository.deleteByName("jasmine"))
		);
		
		return productRepository.findByPriceBetween(priceFrom, priceTo);
	}

	
	public Sort genSortStrategy(String orderBy, String sortRule) {
		Sort sort = Sort.unsorted();
		
		if(Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
			Sort.Direction direction = Direction.fromString(sortRule);
			sort = Sort.by(direction, orderBy);
		}
		
		return sort;
	}
	
	/**
	 * 找出價格低於 xxx 的產品
	 * */
	public List<Product> getProduct(int price){
		return productRepository.findByPriceLessThanEqual(price);
	}
}
