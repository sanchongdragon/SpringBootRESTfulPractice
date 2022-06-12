package com.my.restful.RESTfulPractice.Interface;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.my.restful.RESTfulPractice.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

	
	List<Product> findByNameLike(String name);
	
	// 找出 name 欄位值有包含參數的所有文件，且不分大小寫
	List<Product> findByNameLikeIgnoreCase(String name, Sort sort);

	// 找出 id 欄位值有包含在參數之中的所有文件
	List<Product> findByIdIn(List<String> ids);

	// 找出價格xxx以下的產品
	List<Product> findByPriceLessThanEqual(int price);
	
	// 找出價格在某個區間的產品, 並排序
	List<Product> findByPriceBetweenAndNameLikeIgnoreCase(int priceFrom, int priceTo, String name, Sort sort);
	
	// 使用mongoDB 原生query, 查詢 price >= from <= to 的資料
	// 使用JPA的method Between會只有 >= 跟 <
	@Query("{ 'price' : { '$gte' : ?0, '$lte' : ?1 } }")
	List<Product> findByPriceBetween(int from, int to);
	
	@Query(value = "{ 'name' : { '$regex' : ?0 , '$options' : i } }")
	List<Product> findByNameLikeIgnoreCase(String name);
	
	@Query(value = "{ 'name' : { '$regex' : ?0 , '$options' : i } }", count = true)
	int findByNameLikeIgnoreCaseCount(String name);
	
	@Query(value = "{ 'name' : { '$regex' : ?0 , '$options' : i } }", delete = true)
	int deleteByName(String name);
	
	@Query(" { 'price' : { '$gte' : ?0 , '$lte' : ?1 }, 'name' : { '$regex' : ?2 , '$options' : i } } ")
	List<Product> findByPriceBetweenAndNameLikeIgnoreCase(int priceFrom, int priceTo, String name);
	// 找出 username 與 password 欄位值皆符合參數的一筆文件
//	Optional<User> findByUsernameAndPassword(String email, String pwd);
}
