package com.carfood.model;

import java.util.List;

public class CarFoodService {

	private CarFoodDAO_interface dao;
	
	public CarFoodService() {
		dao = new CarFoodDAO();
	}
	
	public CarFoodVO addCarFood(String cf_foodno,
			String cf_memno, Integer cf_qty) {

		CarFoodVO carfoodVO = new CarFoodVO();

		carfoodVO.setCf_foodno(cf_foodno);
		carfoodVO.setCf_memno(cf_memno);
		carfoodVO.setCf_qty(cf_qty);
		dao.insert(carfoodVO);

		return carfoodVO;
	}
	
	public CarFoodVO updateCarFood(String cf_foodno,
			String cf_memno, Integer cf_qty) {

		CarFoodVO carfoodVO = new CarFoodVO();

		carfoodVO.setCf_foodno(cf_foodno);
		carfoodVO.setCf_memno(cf_memno);
		carfoodVO.setCf_qty(cf_qty);
		dao.update(carfoodVO);

		return carfoodVO;
	}
	
	public void deleteCarFood(String cf_foodno, String cf_memno) {
		dao.delete(cf_foodno, cf_memno);
	}

	public CarFoodVO getOneCarFood(String cf_foodno, String cf_memno) {
		return dao.findByPrimaryKey(cf_foodno, cf_memno);
	}

	public List<CarFoodVO> getAll() {
		return dao.getAll();
	}
	
	//新增by李承璋
	public List<CarFoodVO> getOneCar(String cf_memno) {
		return dao.getOneCar(cf_memno);
	}
	public void updateTransaction(List<CarFoodVO> carFoodVOList) {
		dao.updateTransaction(carFoodVOList);
	}
	//新增by李承璋
}
