package com.search.model;

import java.util.List;

import com.google.gson.JsonObject;

public interface SearchDAO_interface {
	public List<SearchVO> getResult(JsonObject jsonObj);
}