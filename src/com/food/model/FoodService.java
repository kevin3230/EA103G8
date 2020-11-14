package com.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodService {
	
	private FoodDAO_interface dao;

	public FoodService() {
		dao = new FoodDAO();
	}
	
	public FoodVO addFood(String food_vdno, String food_name, String food_ftno, Integer food_price, String food_intro,
			Integer food_stat, byte[] food_pic) {

		FoodVO foodVO = new FoodVO();
				
		foodVO.setFood_vdno(food_vdno);
		foodVO.setFood_name(food_name);
		foodVO.setFood_ftno(food_ftno);
		foodVO.setFood_price(food_price);
		foodVO.setFood_intro(food_intro);
		foodVO.setFood_stat(food_stat);			
		foodVO.setFood_pic(food_pic);
		dao.insert(foodVO);

		return foodVO;
	}
	
	public FoodVO updateFood(String food_no, String food_vdno, String food_name, String food_ftno, Integer food_price, String food_intro,
			Integer food_stat, byte[] food_pic) {

		FoodVO foodVO = new FoodVO();
		
		foodVO.setFood_no(food_no);
		foodVO.setFood_vdno(food_vdno);
		foodVO.setFood_name(food_name);
		foodVO.setFood_ftno(food_ftno);
		foodVO.setFood_price(food_price);
		foodVO.setFood_intro(food_intro);
		foodVO.setFood_stat(food_stat);			
		foodVO.setFood_pic(food_pic);
		dao.update(foodVO);

		return foodVO;
	}
	
	public void deleteFood(String food_no) {
		dao.delete(food_no);
	}
	
	public FoodVO getOneFood(String food_no) {
		return dao.findByPrimaryKey(food_no);
	}
	
	public List<FoodVO> getAll() {
		return dao.getAll();
	}
	
	//新增by李承璋
	public List<FoodVO> getOneVendor(String food_vdno, Integer food_stat, String food_ftno) {
		return dao.getOneVendor(food_vdno, food_stat, food_ftno);
	}
	public List<FoodVO> getVendorFoodType(String food_vdno, Integer food_stat) {
		return dao.getVendorFoodType(food_vdno, food_stat);
	}
	//新增by李承璋
	
	public List<FoodVO> getFoodsByVdno(String vd_no) {
		return dao.getFoodsByVdno(vd_no);
	}
	
	// 把狀態為「-1 刪除」者篩選掉
	public List<FoodVO> getFoodsByVdnoWithoutDeleted(String vd_no) {
		List<FoodVO> list = dao.getFoodsByVdno(vd_no);
		List<FoodVO> withoutDeleted = new ArrayList<FoodVO>();
		for (FoodVO foodVO : list) {
			if (foodVO.getFood_stat() != -1)  {
				withoutDeleted.add(foodVO);
			}
		}
		return withoutDeleted;
		}
	
	// 多筆上架
	public void updateFoodStatTo2(String[] stringArray) {
		for(String camp_no : stringArray) {
			dao.updateFoodStatTo2(camp_no);
		}		
	}
	
	// 多筆下架
		public void updateFoodStatTo1(String[] stringArray) {
			for(String camp_no : stringArray) {
				dao.updateFoodStatTo1(camp_no);
			}		
		}
		
	// 多筆刪除
	public void deleteSelected (String[] stringArray) {
		for(String camp_no : stringArray) {
			dao.delete(camp_no);
		}
	}
	
	// Author Jeff
	public List<FoodVO> getExistFoodsByVdno(String vd_no) {
		List<FoodVO> result = new ArrayList<FoodVO>();
		for(FoodVO foodVOi : dao.getFoodsByVdno(vd_no)) {
			if(foodVOi.getFood_stat() >= 0) {
				result.add(foodVOi);
			}
		}
		return result;
	}
	
	public Set<String> getAllFt_no(String vd_no) {
		return dao.getAllFt_no(vd_no);		
	}
	
	public List<FoodVO> getFoodsByVdnoFtno(String vd_no, String ft_no) {
		return dao.getFoodsByVdnoFtno(vd_no, ft_no);
	}
		
}
