package com.my.restful.RESTfulPractice;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.my.restful.RESTfulPractice.Interface.ProductRepository;
import com.my.restful.RESTfulPractice.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
/**
 * test product controller , with 3A test
 * */
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	private HttpHeaders httpHeaders;
	
	@BeforeEach
	public void initHeader() {
		httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}
	
	@AfterEach
	public void clearRepo() {
		productRepository.deleteAll();
	}
	
	@org.junit.jupiter.api.Test
	void testCreateProduct() throws Exception {
		System.out.println("start testCreateProduct !");
		
		JSONObject request = new JSONObject()
									.put("name", "mary")
									.put("price", 234);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/v1/products")
										.headers(httpHeaders)
										.content(request.toString());
																
		mockMvc.perform(requestBuilder)
				.andDo(print()) // 使用andDo + static method "print" 來 print 出訊息在console
								// 以下使用 andExpect 來測試預期結果
				.andExpect(status().isCreated()) // 預期 status 是建立完成的狀態
				// jsonPath 以 $ 開頭, 用 . 來往下一層路徑
//				.andExpect(jsonPath("$.id").hasJsonPath()) // 預期某個欄位存在於 json 當中
//				.andExpect(jsonPath("$.name").value(request.getString("name"))) // 預期 name 欄位的值為何
//				.andExpect(jsonPath("$.price").value(request.getInt("price"))) // 預期 price 欄位的值為何
				.andExpect(header().exists(HttpHeaders.LOCATION)) // 預期 header 有 location 欄位
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)); // 預期 header 的 contentType 是 application/json		
		System.out.println("end testCreateProduct !");
	}
	
	
	@org.junit.jupiter.api.Test
	void testGetProduct() throws Exception {
		System.out.println("start testGetProduct !");
		
		// 3A 原則 Arrange, 建立測試資料		
		Product insertedProduct = productRepository.insert(createTestProduct("mary have a apple", 810));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get("/v1/products/" + insertedProduct.getId())
										.headers(httpHeaders);
						
		// 3A 原則 Act 用 perform 來完成 Act 測試環節
		// 3A 原則 Assert 用 andExpect 來完成 Assert 驗證環節
		mockMvc.perform(requestBuilder)
				.andDo(print()) // 使用andDo + static method "print" 來 print 出訊息在console
								// 以下使用 andExpect 來測試預期結果
				.andExpect(status().isOk()) // 預期 status 是建立完成的狀態
				// jsonPath 以 $ 開頭, 用 . 來往下一層路徑
				.andExpect(jsonPath("$.id").value(insertedProduct.getId())) // 預期某個欄位存在於 json 當中
				.andExpect(jsonPath("$.name").value(insertedProduct.getName())) // 預期 name 欄位的值為何
				.andExpect(jsonPath("$.price").value(insertedProduct.getPrice())) // 預期 price 欄位的值為何
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)); // 預期 header 的 contentType 是 application/json		
		System.out.println("end testGetProduct !");
	}

	@org.junit.jupiter.api.Test
	void testReplaceProduct() throws Exception {
		System.out.println("start testPutProduct !");
		
		// 3A 原則 Arrange, 建立測試資料		
		Product insertedProduct = productRepository.insert(createTestProduct("mary have a apple", 810));
		JSONObject obj = new JSONObject().put("name", "mary have a banana").put("price", 900);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put("/v1/products/{id}", insertedProduct.getId())
										.headers(httpHeaders)
										.content(obj.toString());
						
		// 3A 原則 Act 用 perform 來完成 Act 測試環節
		// 3A 原則 Assert 用 andExpect 來完成 Assert 驗證環節
		mockMvc.perform(requestBuilder)
				.andDo(print()) // 使用andDo + static method "print" 來 print 出訊息在console
								// 以下使用 andExpect 來測試預期結果
				.andExpect(status().isOk()) // 預期 status 是建立完成的狀態
				// jsonPath 以 $ 開頭, 用 . 來往下一層路徑
				.andExpect(jsonPath("$.id").value(insertedProduct.getId())) // 預期 id 欄位的值為何
				.andExpect(jsonPath("$.name").value(obj.getString("name"))) // 預期 name 欄位的值為何
				.andExpect(jsonPath("$.price").value(obj.getInt("price"))) // 預期 price 欄位的值為何
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)); // 預期 header 的 contentType 是 application/json		
		System.out.println("end testPutProduct !");
	}

	@org.junit.jupiter.api.Test()
	void testDeleteProductWithNoCotentStatus() throws Exception {
		System.out.println("start testDeleteProductWithNoCotentStatus !");
		
		// 3A 原則 Arrange, 建立測試資料		
		Product insertedProduct = productRepository.insert(createTestProduct("mary have a apple", 810));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.delete("/v1/products/" + insertedProduct.getId())
										.headers(httpHeaders);
						
		// 3A 原則 Act 用 perform 來完成 Act 測試環節
		// 3A 原則 Assert 用 andExpect 來完成 Assert 驗證環節
		mockMvc.perform(requestBuilder)
				.andDo(print()) // 使用andDo + static method "print" 來 print 出訊息在console
								// 以下使用 andExpect 來測試預期結果
				.andExpect(status().isNoContent()); // 預期 status 是刪除完成的狀態
		System.out.println("end testDeleteProductWithNoCotentStatus !");
	}

	@org.junit.jupiter.api.Test()
	void testDeleteProductWithExpectException() throws Exception {
		System.out.println("start testDeleteProductWithExpectException !");
		
		// 3A 原則 Arrange, 建立測試資料		
		Product insertedProduct = productRepository.insert(createTestProduct("mary have a apple", 810));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.delete("/v1/products/" + insertedProduct.getId())
										.headers(httpHeaders);
		
		// 3A 原則 Act 用 perform 來完成 Act 測試環節
		// 3A 原則 Assert 用 andExpect 來完成 Assert 驗證環節
		mockMvc.perform(requestBuilder)
				.andDo(print()) // 使用andDo + static method "print" 來 print 出訊息在console
								// 以下使用 andExpect 來測試預期結果
				.andExpect(status().isNoContent()); // 預期 status 是刪除完成的狀態
		
		// junit 5 新的 assert, assertThrows 斷言將會被丟出的 exception
		// excutable 可以使用 lambda express 來表示想要測試丟出 exception 的行為
		// junit 5 以前, 是寫在 @Test(expected = "RuntimeException.class") 裡面
		RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,
				() -> productRepository.findById(insertedProduct.getId()).orElseThrow(() -> new RuntimeException("RuntimeException Be Thrown")),
				"Expected RuntimeException Not Be Thrown");
		
		// 斷言被丟出的 RuntimeException message 是否符合預期
		assertTrue(runtimeException.getMessage().contains("RuntimeException"));
		
		System.out.println("end testDeleteProductWithExpectException !");
	}
	
	@org.junit.jupiter.api.Test
	void testSearchProductByPriceAsc() throws Exception {
		System.out.println("start testSearchProductByPriceAsc !");
		
		// 3A 原則 Arrange, 建立測試資料		
		Product insertedProduct1 = createTestProduct("mary have a apple", 100);
		Product insertedProduct2 = createTestProduct("mary have a banana", 200);
		Product insertedProduct3 = createTestProduct("mary have a cake", 300);
		Product insertedProduct4 = createTestProduct("mary have a doge", 400);
		Product insertedProduct5 = createTestProduct("mary have a egg", 500);

		List<Product> insertedProductList = productRepository.insert(Arrays.asList(insertedProduct1, insertedProduct2, insertedProduct3, insertedProduct4, insertedProduct5));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get("/v1/products/sort")
										.headers(httpHeaders)
										// 這邊透過 query string 傳入 parameter
										.param("keyword", "mary")
										.param("orderBy", "price")
										.param("sortRule", "asc");
						
		// 3A 原則 Act 用 perform 來完成 Act 測試環節
		// 3A 原則 Assert 用 andExpect 來完成 Assert 驗證環節
		mockMvc.perform(requestBuilder)
				.andDo(print()) // 使用andDo + static method "print" 來 print 出訊息在console
								// 以下使用 andExpect 來測試預期結果
				.andExpect(status().isOk()) // 預期 status 是建立完成的狀態
				// jsonPath 以 $ 開頭, 用 . 來往下一層路徑
				.andExpect(jsonPath("$.size()").value(5)) // $.size or $.length() 可以計算預期body的size or length
				.andExpect(jsonPath("$[0].id").value(insertedProductList.get(0).getId())) // 預期 id[0] 欄位的值為何
				.andExpect(jsonPath("$[1].id").value(insertedProductList.get(1).getId())) // 預期 id[1] 欄位的值為何
				.andExpect(jsonPath("$[2].id").value(insertedProductList.get(2).getId())) // 預期 id[2] 欄位的值為何
				.andExpect(jsonPath("$[3].id").value(insertedProductList.get(3).getId())) // 預期 id[3] 欄位的值為何
				.andExpect(jsonPath("$[4].id").value(insertedProductList.get(4).getId())) // 預期 id[4] 欄位的值為何
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)); // 預期 header 的 contentType 是 application/json		
		System.out.println("end testSearchProductByPriceAsc !");
	}
	
	@Test
	void testSearchProductByPriceDesc() throws Exception {
		System.out.println("start testSearchProductByPriceDesc !");
		
		// 3A 原則 Arrange, 建立測試資料		
		Product insertedProduct1 = productRepository.insert(createTestProduct("mary have a apple", 100));
		Product insertedProduct2 = productRepository.insert(createTestProduct("mary have a banana", 200));
		Product insertedProduct3 = productRepository.insert(createTestProduct("mary have a cake", 300));
		Product insertedProduct4 = productRepository.insert(createTestProduct("mary have a doge", 400));
		Product insertedProduct5 = productRepository.insert(createTestProduct("mary have a egg", 500));

//		List<Product> insertedProductList = productRepository.insert(Arrays.asList(insertedProduct1, insertedProduct2, insertedProduct3, insertedProduct4, insertedProduct5));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get("/v1/products/sort")
										.headers(httpHeaders)
										// 這邊透過 query string 傳入 parameter
										.param("keyword", "mary")
										.param("orderBy", "price")
										.param("sortRule", "desc");
						
		// 3A 原則 Act 用 perform 來完成 Act 測試環節
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		String responseContent = response.getContentAsString();
		JSONArray jsonArray = new JSONArray(responseContent);
		List<String> productIds = new ArrayList<>();
		for(int i = 0 ; i < jsonArray.length() ; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			productIds.add(jsonObject.getString("id"));
		}
		
		// 3A 原則 Assert 用 andExpect 來完成 Assert 驗證環節
		// 斷言取出的 products ids 數量與 response 轉出來的 jsonarray 一樣
		Assert.assertEquals(5, productIds.size());
		
		// 斷言取出的 ids 是否符合排序
		Assert.assertEquals(insertedProduct5.getId(), productIds.get(0));
		Assert.assertEquals(insertedProduct4.getId(), productIds.get(1));
		Assert.assertEquals(insertedProduct3.getId(), productIds.get(2));
		Assert.assertEquals(insertedProduct2.getId(), productIds.get(3));
		Assert.assertEquals(insertedProduct1.getId(), productIds.get(4));
		
		// 斷言取出的 ids 是否包含所輸入的結果
		Assert.assertTrue(productIds.contains(insertedProduct1.getId()));
		Assert.assertTrue(productIds.contains(insertedProduct2.getId()));
		Assert.assertTrue(productIds.contains(insertedProduct3.getId()));
		Assert.assertTrue(productIds.contains(insertedProduct4.getId()));
		Assert.assertTrue(productIds.contains(insertedProduct5.getId()));

		// 斷言取出的 response 狀態以及 contentType
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

		System.out.println("end testSearchProductByPriceDesc !");
	}

	@Test
	/**
	 * 測試Post如果輸入不符合POJO限制的content預期會給出400的status
	 * */
	void testResponse400CreateProduct() throws Exception{
		
		// 3A Arrange 建立假資料
		JSONObject content = new JSONObject().put("name", "").put("price", -1);
		
		RequestBuilder builder = MockMvcRequestBuilders
									.post("/v1/products")
									.headers(httpHeaders)
									.content(content.toString());
		// 3A Act 執行, Assert 斷言
		mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isBadRequest());
				
	}
	
	@Test
	/**
	 * 測試Put如果輸入不符合POJO限制的content預期會給出400的status
	 * */
	void testResponse400ReplaceProduct() throws Exception{
		
		// 3A Arrange 建立測資
		Product bananaProduct = new Product();
		bananaProduct.setName("banana");
		bananaProduct.setPrice(500);
		Product insertedProduct = productRepository.insert(bananaProduct);
		JSONObject content = new JSONObject().put("name", null).put("price", -100);
		
		RequestBuilder builder = MockMvcRequestBuilders
									.put("/v1/products/{id}", insertedProduct.getId())
									.headers(httpHeaders)
									.content(content.toString());
		
		// 3A Act 執行, Assert斷言
		mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isBadRequest());
				
	}

	private Product createTestProduct(String name, int price) {
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		return product;
	}
	
}
