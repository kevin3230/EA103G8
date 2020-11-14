package com.careqpt.model;

import java.sql.Date;
import java.util.List;

import com.eqptavail.model.EqptAvailVO;
import com.sun.org.apache.regexp.internal.recompile;

public class CarEqptService2 {

	private CarEqptDAO_interface2 dao;

	public CarEqptService2() {
		dao = new CarEqptDAO2();
	}

	public List<CarEqptVO2> getAllBymem_no(String cc_memno){
		return dao.getAllBymem_no(cc_memno);
	}
	
	public CarEqptVO2 getOneEqptAvail(String ea_eqptno,  Date ea_date) {
		return dao.getOneEqptAvail(ea_eqptno, ea_date);
	}
	
	public List<CarEqptVO2> getEqptAvailsByeadate(Date start){
		return dao.getEqptAvailsByeadate(start);
	}
	
	public CarEqptVO2 getOnePic(String eqpt_no) {
		return dao.getOnePic(eqpt_no);
	}
	public List<CarEqptVO2> getMinEqptPrice(String ea_eqptno) {
		return dao.getMinEqptPrice(ea_eqptno);
	}
	
}
