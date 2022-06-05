package com.my.restful.RESTfulPractice.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.my.restful.RESTfulPractice.entity.Product;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter;

@Repository
public class MockProductDAO {
	private List<Product> productList = new ArrayList<>();
	
	@PostConstruct
	public void initDB() {
		productList.add(new Product("001", "mary have apple", 100));
		productList.add(new Product("002", "mary have banana", 200));
		productList.add(new Product("003", "mary have cake", 300));
		productList.add(new Product("004", "mary have doge", 400));
		productList.add(new Product("005", "mary have egg", 500));
	}
	
	/**
	 * 插入新的產品
	 * */
	public Product insertProduct(Product product) {
		productList.add(product);
		return product;
	}
	
	/**
	 * 找尋id產品
	 * */
	public Optional<Product> findId(String id){
		return productList.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst();
	}
	
	/**
	 * 替換同id的產品名稱跟價格
	 * */
	public Product replaceProduct(String id, Product product) {
		
		Optional<Product> op = findId(id);
		
		op.ifPresent(p -> {
			p.setName(product.getName());
			p.setPrice(product.getPrice());
		});
		
		return product;
	}
	
	/**
	 * 移除產品
	 * */
	public boolean deleteProduct(String id) {
		return productList.removeIf(p -> p.getId().equalsIgnoreCase(id));
	}
	
	public List<Product> findProductList(ProductQueryParameter parameter){
		String keyword = Optional.ofNullable(parameter.getKeyword()).orElse("");
		String orderBy = parameter.getOrderBy();
		String sortRule = parameter.getSortRule();
		
		Comparator<Product> comparator = genSortComparator(orderBy, sortRule);
		
		return productList.stream().filter(p -> p.getName().contains(keyword)).sorted(comparator).collect(Collectors.toList());
		
	}
	
	
	private Comparator<Product> genSortComparator(String orderBy, String sortRule){
		// 依照name -> price -> id排序
		Comparator<Product> comparator = Comparator.comparing(Product :: getName)
											.thenComparing(Comparator.comparing(Product :: getPrice))
											.thenComparing(Comparator.comparing(Product :: getId));
		
		
		
//		if(Objects.isNull(sortRule) || Objects.isNull(orderBy))
//			return comparator;
		
		// 根據orderBy來改變comparator的順序
//		if(orderBy.equalsIgnoreCase("price"))
//			comparator = Comparator.comparing(Product :: getPrice);
//		else if(orderBy.equalsIgnoreCase("name"))
//			comparator = Comparator.comparing(Product :: getName);
//		else if(orderBy.equalsIgnoreCase("id"))
//			comparator = Comparator.comparing(Product :: getId);
				
		return sortRule.equalsIgnoreCase("desc") ? comparator.reversed() : comparator ;
	}

}
