package com.food.model;

import java.util.*;

import com.camp.model.CampVO;

public interface FoodDAO_interface {
	public void insert(FoodVO foodVO);
	public void update(FoodVO foodVO);
	public void delete(String food_no);
	public FoodVO findByPrimaryKey(String food_no);
	public List<FoodVO> getAll();
	//新增by李承璋
	public List<FoodVO> getOneVendor(String food_vdno, Integer food_stat, String food_ftno);
	public List<FoodVO> getVendorFoodType(String food_vdno, Integer food_stat);
	//新增by李承璋
	
	public List<FoodVO> getFoodsByVdno(String vd_no);
	
	// 多筆上架
	public void updateFoodStatTo2(String food_no);
	// 多筆下架
	public void updateFoodStatTo1(String food_no);
	
	public Set<String> getAllFt_no(String vd_no);
	public List<FoodVO> getFoodsByVdnoFtno(String vd_no, String ft_no);
}
