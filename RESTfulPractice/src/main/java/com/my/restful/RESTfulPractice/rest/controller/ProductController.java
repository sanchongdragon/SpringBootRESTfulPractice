package com.my.restful.RESTfulPractice.rest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.my.restful.RESTfulPractice.entity.Product;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter;
import com.my.restful.RESTfulPractice.service.ProductService;

@RestController
@RequestMapping(value = "/v1",
				consumes = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 根據id取得特定產品
	 * @param id
	 * @return 產品entity
	 * */
	@GetMapping(path = "/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
		System.out.printf("getProduct method variable : %s %n", id);

		Product product = productService.getProduct(id);
		return ResponseEntity.ok().body(product);
	}
	
//	@GetMapping(path = "/products")
//	public ResponseEntity<List<Product>> getSpecificProduct(
//			@RequestParam(value = "keyword", defaultValue = "") String name){
//		System.out.printf("getSpecificProduct method %n");
//		
//		List<Product> productList = emulatorProductDB.stream()
//													.filter(p -> p.getName().toUpperCase().contains(name.toUpperCase()))
//													.collect(Collectors.toList());
//		
//
//		return ResponseEntity.ok().body(productList);
//	}
	
	@GetMapping(path = "/products")
	public ResponseEntity<List<Product>> getSpecificProduct(
			@ModelAttribute ProductQueryParameter parameter ){
		System.out.printf("getSpecificProduct method %n");

		List<Product> productList = productService.getProduct(parameter);
		return ResponseEntity.ok().body(productList);
	}

	
	
	/**
	 * 新增產品
	 * @param requestBody
	 * */
	@PostMapping(path = "/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product requestBody) {
		System.out.printf("createProduct method %n");

		Product product = productService.createProduct(requestBody);
		
		// 利用ServletUriComponentsBuilder來建立新的資源路徑
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest() // 取用當前的request
						.path("/{id}") // 往後延伸路徑, 可使用佔位符
						.buildAndExpand(product.getId()) // 將request中的物件id放入路徑當中
						.toUri(); 
		// created 須包含一個新的資源路徑(URI)
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * 更換產品名稱、價格
	 * @param id
	 * @param requestBody
	 * */
	@PutMapping(path = "/products/{id}")
	public ResponseEntity<Product> replaceProduct(@PathVariable("id") String id, @RequestBody Product requestBody){
		Product product = productService.replaceProduct(id, requestBody);
		return ResponseEntity.ok().body(product);
	}
	
	/**
	 * 刪除產品
	 * @param id
	 * */
	@DeleteMapping(path = "products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id){
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}	
}
