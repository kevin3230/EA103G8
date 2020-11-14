package com.careqpt.model;

import java.sql.Date;
import java.util.List;

public interface CarEqptDAO_interface2 {

	List<CarEqptVO2> getAllBymem_no(String cc_memno);


	CarEqptVO2 getOneEqptAvail(String ea_eqptno, Date ea_date);




	List<CarEqptVO2> getEqptAvailsByeadate(Date start);


	CarEqptVO2 getOnePic(String eqpt_no);


	List<CarEqptVO2> getMinEqptPrice(String ea_eqptno); 
}
