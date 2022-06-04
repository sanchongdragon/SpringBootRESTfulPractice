package com.my.restful.RESTfulPractice.rest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.my.restful.RESTfulPractice.entity.Product;
import com.my.restful.RESTfulPractice.rest.controller.queryparameter.ProductQueryParameter;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	private final List<Product> emulatorProductDB = new ArrayList<>();
	
	/**
	 * 假裝有DB資料
	 * */
	@PostConstruct
	public void initDB() {
		emulatorProductDB.add(new Product("001", "mary have cat", 100));
		emulatorProductDB.add(new Product("002", "mary have dog", 200));
		emulatorProductDB.add(new Product("003", "mary have bird", 300));
		emulatorProductDB.add(new Product("004", "mary have snack", 400));
		emulatorProductDB.add(new Product("005", "mary have shark", 500));
	}
	
	/**
	 * 根據id取得特定產品
	 * @param id
	 * @return 產品entity
	 * */
	@GetMapping(path = "/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
		System.out.printf("getProduct method variable : %s %n", id);
		Product product = new Product();
				
		if(emulatorProductDB.isEmpty())
			return ResponseEntity.notFound().build();
		
		Optional<Product> op = emulatorProductDB
										.stream()
										.filter(p -> p.getId().equals(id))
										.findFirst();
		
		if(op.isPresent()) // isPresent true 就是有東西, false 是沒東西
			return ResponseEntity.ok().body(op.get());
		else
			return ResponseEntity.notFound().build();
		
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
		String keyword = parameter.getKeyword();
		String orderBy = parameter.getOrderBy();
		String sortRule = parameter.getSortRule();
		
		Comparator<Product> comparator = genSortComparator(orderBy, sortRule);
		
		List<Product> productList = emulatorProductDB.stream()
													.filter(p -> p.getName().toUpperCase().contains(keyword.toUpperCase()))
													.sorted(comparator)
													.collect(Collectors.toList());

		return ResponseEntity.ok().body(productList);
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
	
	
	/**
	 * 新增產品
	 * @param requestBody
	 * */
	@PostMapping(path = "/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product requestBody) {
		System.out.printf("createProduct method %n");
		// 檢查是否重複
		boolean isDuplicate = emulatorProductDB
								.stream()
								.anyMatch(p -> p.getId().equals(requestBody.getId()));
		
		if(isDuplicate) // 重複就回傳 422
			return ResponseEntity.unprocessableEntity().build();
		
		Product product = new Product();
		product.setId(requestBody.getId());
		product.setName(requestBody.getName());
		product.setPrice(requestBody.getPrice());
		
		emulatorProductDB.add(product);
		
		// 利用ServletUriComponentsBuilder來建立新的資源路徑
		URI location = ServletUriComponentsBuilder
						.fromCurrentRequest() // 取用當前的request
						.path("/{id}") // 往後延伸路徑, 可使用佔位符
						.buildAndExpand(requestBody.getId()) // 將request中的物件id放入路徑當中
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
		
		Optional<Product> op = emulatorProductDB.stream()
												.filter(p -> p.getId().equals(id))
												.findFirst();
		if(op.isPresent()) { // 如果有找到同id的產品, 就替換名稱、價格
			Product p = op.get();
			p.setName(requestBody.getName());
			p.setPrice(requestBody.getPrice());
			
			return ResponseEntity.ok().body(p);
		}else { // 沒找到就回404
			return ResponseEntity.notFound().build();
		}
		
	}
	
	/**
	 * 刪除產品
	 * @param id
	 * */
	@DeleteMapping(path = "products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id){
		// remove成功就回true
		boolean removed = emulatorProductDB.removeIf(p -> p.getId().equals(id));
		// remove成功就回204, 失敗就回404
		return removed ? 
				ResponseEntity.noContent().build() :
				ResponseEntity.notFound().build();
	}
	
}
