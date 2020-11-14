package com.camp.model;

import java.util.*;

public interface CampDAO_interface {
	public void insert(CampVO campVO);
	public void update(CampVO campVO);
	public void delete(String camp_no);
	public CampVO findByPrimaryKey(String camp_no);
	public List<CampVO> getAll();
	
	// Add by Yen-Fu Chen
	public List<CampVO> getCampsByVdno(String vd_no);
	
	// 多筆上架
	public void updateCampStatTo2(String camp_no);
	// 多筆下架
	public void updateCampStatTo1(String camp_no);
	
	
	public Set<String> getAllCt_no(String vd_no);
	public List<CampVO> getCampsByVdnoCtno(String vd_no, String ct_no);
	
	// Author Jeff
	public List<CampVO> getExistCampsByVdno(String vd_no);
	
}
