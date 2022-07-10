package com.my.restful.RESTfulPractice.rest.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.my.restful.RESTfulPractice.entity.Product;
import com.my.restful.RESTfulPractice.requestModel.ProductRequest;
import com.my.restful.RESTfulPractice.requestModel.ProductResponse;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter2;
import com.my.restful.RESTfulPractice.service.ProductService;
import com.my.restful.RESTfulPractice.utils.ProductConveter;

@RestController
@RequestMapping(value = "/v1",
				produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	private final String MAX = "2147483647";
	@Autowired
	private ProductService productService;
	
	/**
	 * 根據id取得特定產品
	 * @param id
	 * @return 產品entity
	 * */
	@GetMapping(path = "/products/{id}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") String id) {
		System.out.printf("getProduct method variable : %s %n", id);

		ProductResponse product = productService.getProduct(id);
		return ResponseEntity.ok().body(product);
	}
	
	/**
	 * 根據id取得特定產品
	 * @param id
	 * @return 產品entity
	 * */
	@GetMapping(path = "/products")
	public ResponseEntity<List<Product>> getAllProduct() {
		System.out.printf("getAllProduct method %n");

		List<Product> product = productService.getAllProduct();
		return ResponseEntity.ok().body(product);
	}
	
	/**
	 * 根據price取得特定產品
	 * @param price
	 * @return 產品entity
	 * */
	@GetMapping(path = "/products/less")
	public ResponseEntity<List<Product>> getProduct(@RequestParam(value = "price", defaultValue = "0") int price) {
		System.out.printf("getProduct method variable : %s %n", price);

		List<Product> productList = productService.getProduct(price);
		return ResponseEntity.ok().body(productList);
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
	
	@GetMapping(path = "/products/spec")
	public ResponseEntity<List<Product>> getSpecificProduct(
			@ModelAttribute ProductQueryParameter2 parameter ){
		System.out.printf("getSpecificProduct method %n");

		List<Product> productList = productService.getProduct(parameter);
		return ResponseEntity.ok().body(productList);
	}
	
	@GetMapping(path = "/products/sort")
	public ResponseEntity<List<Product>> getSpecificSortProduct(
			@ModelAttribute ProductQueryParameter2 parameter ){
		System.out.printf("getSpecificSortProduct method %n");

		List<Product> productList = productService.getSortProduct(parameter);
		return ResponseEntity.ok().body(productList);
	}

	@GetMapping(path = "/products/between")
	public ResponseEntity<List<Product>> getSpecificProduct(
			@RequestParam(value = "priceFrom", defaultValue = "0", required = false) int priceFrom, 
			@RequestParam(value = "priceTo", defaultValue = MAX, required = false) int priceTo){
		System.out.printf("getSpecificProduct method %n");

		List<Product> productList = productService.getProduct(priceFrom, priceTo);
		return ResponseEntity.ok().body(productList);
	}
	
	/**
	 * 新增產品
	 * @param requestBody
	 * */
	@PostMapping(path = "/products")
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest requestBody) {
		System.out.printf("createProduct method %n");

		ProductResponse product = productService.createProduct(requestBody);
		
		// 利用ServletUriComponentsBuilder來建立新的資源路徑
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest() // 取用當前的request
						.path("/{id}") // 往後延伸路徑, 可使用佔位符
						.buildAndExpand(product.getId()) // 將request中的物件id放入路徑當中
						.toUri(); 
		// created 須包含一個新的資源路徑(URI)
		return ResponseEntity.created(location).contentType(MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * 更換產品名稱、價格
	 * @param id
	 * @param requestBody
	 * */
	@PutMapping(path = "/products/{id}")
	public ResponseEntity<ProductResponse> replaceProduct(@PathVariable("id") String id,@Valid @RequestBody ProductRequest requestBody){

		ProductResponse product = productService.replaceProduct(id, requestBody);
		return ResponseEntity.ok().body(product);
	}
	
	/**
	 * 刪除產品
	 * @param id
	 * */
	@DeleteMapping(path = "/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id){
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 刪除所有產品
	 * @param id
	 * */
	@DeleteMapping(path = "/products")
	public ResponseEntity<Product> deleteAllProduct(){
		productService.deleteAllProduct();
		return ResponseEntity.noContent().build();
	}

	
	
}
