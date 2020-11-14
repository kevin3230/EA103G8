package com.careqpt.model;

import java.sql.Date;
import java.util.List;

public class CarEqptService {

	private CarEqptDAO_interface dao;

	public CarEqptService() {
		dao = new CarEqptDAO();
	}

	public CarEqptVO addCarEqpt(String ce_eqptno, String ce_memno, Integer ce_qty, Date ce_expget, Date ce_expback) {
		CarEqptVO carEqptVO = new CarEqptVO();
		carEqptVO.setCe_eqptno(ce_eqptno);
		carEqptVO.setCe_memno(ce_memno);
		carEqptVO.setCe_qty(ce_qty);
		carEqptVO.setCe_expget(ce_expget);
		carEqptVO.setCe_expback(ce_expback);
		dao.add(carEqptVO);
		return carEqptVO;
	}

	public CarEqptVO updateCarEqpt(String ce_no, Integer ce_qty) {
		CarEqptVO carEqptVO = new CarEqptVO();
		carEqptVO.setCe_no(ce_no);
		carEqptVO.setCe_qty(ce_qty);
		dao.updata(carEqptVO);
		return carEqptVO;
	}

	public void deleteCarEqpt(String ce_memno) {
		dao.delete(ce_memno);
	}

	public CarEqptVO getOneEmp(String ce_no) {
		return dao.findByPrimaryKey(ce_no);
	}

	public List<CarEqptVO> getAll() {
		return dao.getAll();
	}

	// Author Jeff
	public List<CarEqptVO> getCarEqptsByMemno(String mem_no) {
		return dao.getCarEqptsByMemno(mem_no);
	}
}
