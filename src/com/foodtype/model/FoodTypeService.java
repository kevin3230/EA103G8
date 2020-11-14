package com.foodtype.model;

import java.util.List;

public class FoodTypeService {
	
	private FoodTypeDAO_interface dao;
	
	public FoodTypeService() {
		dao = new FoodTypeDAO();
	}
	
	public FoodTypeVO addFoodType(String ft_name) {
		FoodTypeVO foodTypeVO = new FoodTypeVO();
		
		foodTypeVO.setFt_name(ft_name);
		dao.insert(foodTypeVO);
		
		return foodTypeVO;
	}
	
	public FoodTypeVO updateFoodType(String ft_no, String ft_name) {
		FoodTypeVO foodTypeVO = new FoodTypeVO();
		
		foodTypeVO.setFt_no(ft_no);
		foodTypeVO.setFt_name(ft_name);
		dao.update(foodTypeVO);
		
		return foodTypeVO;
	}
	
	public void deleteFoodType(String ft_no) {
		dao.delete(ft_no);
	}
	
	public FoodTypeVO getOneFoodType(String ft_no) {
		return dao.findByPrimaryKey(ft_no);
	}
	
	public List<FoodTypeVO> getAll() {
		return dao.getAll();
	}	
	
}
