package com.eqpttype.model;

import java.util.List;

public class EqptTypeService {
	private EqptTypeDAO_interface dao;
	
	public EqptTypeService() {
		dao = new EqptTypeDAO();
	}
	
	public EqptTypeVO addEqptType(String et_name) {
		EqptTypeVO eqptTypeVO = new EqptTypeVO();
		
		eqptTypeVO.setEt_name(et_name);
		dao.insert(eqptTypeVO);
		
		return eqptTypeVO;
	}
	
	public EqptTypeVO updateEqptType(String et_no, String et_name) {
		EqptTypeVO eqptTypeVO = new EqptTypeVO();
		
		eqptTypeVO.setEt_no(et_no);
		eqptTypeVO.setEt_name(et_name);
		dao.update(eqptTypeVO);
		
		return eqptTypeVO;
	}
	
	public void deleteEqptType(String et_no) {
		dao.delete(et_no);
	}
	
	public EqptTypeVO getOneEqptType(String et_no) {
		return dao.findByPrimaryKey(et_no);
	}
	
	public List<EqptTypeVO> getAll() {
		return dao.getAll();
	}	
	
}
