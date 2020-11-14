package com.camptype.model;

import java.util.List;

public interface CampTypeDAO_interface {
	public void insert(CampTypeVO campTypeVO);
	public void update(CampTypeVO campTypeVO);
	public void delete(String ct_no);
	public CampTypeVO findByPrimaryKey(String ct_no);
	public List<CampTypeVO> getAll();
}
