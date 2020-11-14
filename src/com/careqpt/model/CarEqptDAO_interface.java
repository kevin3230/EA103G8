package com.careqpt.model;

import java.sql.Connection;
import java.util.List;

public interface CarEqptDAO_interface {
	void add(CarEqptVO carEqptVO);

	
	void delete(String mem_no);
	
	void delete(CarEqptVO car_eqptVO); // Author: Jeff
	
	void delete(CarEqptVO car_eqptVO, Connection conn); // Author: Jeff

	CarEqptVO findByPrimaryKey(String ce_no);// 使用表個資料型別來取得一筆資料

	List<CarEqptVO> getAll(); // 使用List來儲存所有表格資料
	
	List<CarEqptVO> getCarEqptsByMemno(String mem_no); // Author: Jeff
	
	String insert(CarEqptVO car_eqptVO); // Author: Jeff

	void updata(CarEqptVO carEqptVO);

	

}
