package com.foodtype.model;

import java.util.List;

public interface FoodTypeDAO_interface {
	public void insert(FoodTypeVO foodTypeVO);
	public void update(FoodTypeVO foodTypeVO);
	public void delete(String ft_no);
	public FoodTypeVO findByPrimaryKey(String ft_no);
	public List<FoodTypeVO> getAll();
}
