package com.my.restful.RESTfulPractice.rest.controller.queryparameter;


public class ProductQueryParameter2 {
	private String keyword;
	private int priceFrom;
	private int priceTo;
	private String orderBy;
	private String sortRule;

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getPriceFrom() {
		return priceFrom;
	}
	public int getPriceTo() {
		return priceTo;
	}
	public void setPriceFrom(int priceFrom) {
		this.priceFrom = priceFrom;
	}
	public void setPriceTo(int priceEnd) {
		this.priceTo = priceEnd;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public String getSortRule() {
		return sortRule;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public void setSortRule(String sortRule) {
		this.sortRule = sortRule;
	}

}
