package com.carcamp.model;

import java.sql.Date;
import java.util.List;

public class CarCampService {
	private CarCampDAO_interface dao;
	
	public CarCampService() {
		dao = new CarCampDAO();
	}
	
	public CarCampVO addCarCamp(String cc_campno, String cc_memno, Integer cc_qty,
			Date cc_start, Date cc_end) {
		CarCampVO carCampVO = new CarCampVO();
				
		carCampVO.setCc_campno(cc_campno);
		carCampVO.setCc_memno(cc_memno);
		carCampVO.setCc_qty(cc_qty);
		carCampVO.setCc_start(cc_start);
		carCampVO.setCc_end(cc_end);
		dao.insert(carCampVO);
		
		return carCampVO;
	}
	
	public CarCampVO updateCarCamp(String cc_campno, String cc_memno, Integer cc_qty,
			Date cc_start, Date cc_end) {
		CarCampVO carCampVO = new CarCampVO();
		
		carCampVO.setCc_campno(cc_campno);
		carCampVO.setCc_memno(cc_memno);
		carCampVO.setCc_qty(cc_qty);
		carCampVO.setCc_start(cc_start);
		carCampVO.setCc_end(cc_end);
		dao.update(carCampVO);
		
		return carCampVO;
	}
	
	public void deleteCarCamp(String cc_memno, String cc_campno) {
		dao.delete(cc_memno, cc_campno);
	}
	
	public void deleteCarCamp(String cc_memno) {
		dao.delete(cc_memno);
	}
	
	public CarCampVO getOneCarCamp(String cc_memno, String cc_campno) {
		return dao.findByPrimaryKey(cc_memno, cc_campno);
	}
	
	public List<CarCampVO> getAll() {
		return dao.getAll();
	}
	
	public List<CarCampVO> getCarCampsByMemno(String mem_no) {
		return dao.getCarCampsByMemno(mem_no);
	}
}