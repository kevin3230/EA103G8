package com.carfood.model;

import java.sql.Connection;
import java.util.*;

public interface CarFoodDAO_interface {
	public void insert(CarFoodVO CarFoodVO);
	public void update(CarFoodVO CarFoodVO);
	public void delete(String cf_foodno, String cf_memno);
	public void delete(CarFoodVO CarFoodVO, Connection con);
	public CarFoodVO findByPrimaryKey(String cf_foodno, String cf_memno);
	public List<CarFoodVO> getAll();
	//新增by李承璋
	public List<CarFoodVO> getOneCar(String cf_memno);
	public void updateTransaction(List<CarFoodVO> carFoodVOList);
	//新增by李承璋
	
}
