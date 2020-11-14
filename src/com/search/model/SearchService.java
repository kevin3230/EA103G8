package com.search.model;

import java.util.List;

import com.google.gson.JsonObject;

public class SearchService {
	private SearchDAO_interface dao;
	
	public SearchService() {
		dao = new SearchDAO();
	}
	
	public List<SearchVO> getResult(JsonObject jsonObj) {
		return dao.getResult(jsonObj);
	}
}