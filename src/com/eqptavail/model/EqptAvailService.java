package com.eqptavail.model;

import java.sql.Date;
import java.util.List;

public class EqptAvailService {

	private EqptAvailDAO_interface dao;

	public EqptAvailService() {
		dao = new EqptAvailDAO();
	}

	public EqptAvailVO addEqptAvail( String ea_eqptno, Date ea_date,  Integer ea_qty) {

		EqptAvailVO eqptAvailVO = new EqptAvailVO();

		eqptAvailVO.setEa_eqptno(ea_eqptno);
		eqptAvailVO.setEa_date(ea_date);
		eqptAvailVO.setEa_qty(ea_qty);


		dao.add(eqptAvailVO);

		return eqptAvailVO;
	}

	public EqptAvailVO updateEqptAvail( String ea_eqptno, Date ea_date,  Integer ea_qty) {

		EqptAvailVO eqptAvailVO = new EqptAvailVO();
		
		
		eqptAvailVO.setEa_eqptno(ea_eqptno);
		eqptAvailVO.setEa_date(ea_date);
		eqptAvailVO.setEa_qty(ea_qty);	

		dao.updata(eqptAvailVO);

		return eqptAvailVO;
	}

	public void deleteEqptAvail(String ea_eqptno,  Date ea_date) {
		dao.delete(ea_eqptno, ea_date);
	}

	public EqptAvailVO getOne(String ea_eqptno,  Date ea_date) {
		return dao.findByPrimaryKey(ea_eqptno, ea_date);
	}

	public List<EqptAvailVO> getAll() {
		return dao.getAll();
	}

	public List<EqptAvailVO> getAllByEqptno(String ea_eqptno) {
		return dao.getAllByEqptno(ea_eqptno);
	}
}
