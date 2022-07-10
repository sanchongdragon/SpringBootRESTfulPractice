package com.my.restful.RESTfulPractice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;

class ObjectMapperTest {

	private ObjectMapper objMapper = new ObjectMapper();
	@Test
	void testSerializeBookToJSON() throws Exception {
		// Arrange 建立假資料
		Book book = new Book();
		book.setIsbn("1234-456-789-0");
		book.setPrice(1000);
		book.setId("A00001");
		book.setName("mary have a big hotdog");
		book.setCreatedTime(new Date());
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String jsonString = objMapper.writeValueAsString(book);
		JSONObject bookJson = new JSONObject(jsonString);
		
		Assert.assertTrue(book.getId().equals(bookJson.getString("id")));
		Assert.assertTrue(book.getName().equals(bookJson.getString("name")));
		Assert.assertTrue(book.getPrice() == bookJson.getInt("price"));
//		Assert.assertTrue(book.getIsbn().equals(bookJson.getString("isbn")));
		Assert.assertEquals(formater.format(book.getCreatedTime()), bookJson.getString("createdTime"));

	}
	
	@Test
	void testSerializeJsonToPublisher() throws Exception {
		// Arrange 建立假資料
		JSONObject jsonPublisher = new JSONObject()
										.put("companyName", "57mCompany")
										.put("address", "三重區重新路三段47巷5弄2號4樓3C")
										.put("telephone", "0952670920");
		Publisher publisher = objMapper.readValue(jsonPublisher.toString(), Publisher.class);
		Assert.assertEquals(jsonPublisher.getString("address"), publisher.getAddress());
		Assert.assertEquals(jsonPublisher.getString("companyName"), publisher.getCompanyName());
		Assert.assertEquals(jsonPublisher.getString("telephone"), publisher.getTel());

	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private static class Book{
		
		private String id;
        private String name;
        private int price;
        @JsonIgnore
        private String isbn;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdTime;
        @JsonUnwrapped
        private Publisher publisher;
		public String getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public int getPrice() {
			return price;
		}
		public String getIsbn() {
			return isbn;
		}
		public Date getCreatedTime() {
			return createdTime;
		}
		public Publisher getPublisher() {
			return publisher;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}
		public void setCreatedTime(Date createdTime) {
			this.createdTime = createdTime;
		}
		public void setPublisher(Publisher publisher) {
			this.publisher = publisher;
		}
		
	}
	
	private static class Publisher{
		
		private String companyName;
        private String address;
        @JsonProperty("telephone")
        private String tel;
		public String getCompanyName() {
			return companyName;
		}
		public String getAddress() {
			return address;
		}
		public String getTel() {
			return tel;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
        
	}
}
