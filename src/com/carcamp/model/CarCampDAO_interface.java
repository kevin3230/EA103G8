package com.carcamp.model;

import java.sql.Connection;
import java.util.List;

public interface CarCampDAO_interface {
	public void insert(CarCampVO carCampVO);
	public void update(CarCampVO carCampVO);
	public void delete(String cc_memno, String cc_campno);
	public void delete(String cc_memno);
	public CarCampVO findByPrimaryKey(String cc_memno, String cc_campno);
	public List<CarCampVO> getAll();
	// 用會員編號查詢
	public List<CarCampVO> getCarCampsByMemno(String mem_no);
	// Author Jeff
	public void delete(CarCampVO carCampVO, Connection conn);
}
