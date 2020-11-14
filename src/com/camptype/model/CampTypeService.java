package com.camptype.model;

import java.util.List;

public class CampTypeService {
	private CampTypeDAO_interface dao;
	
	public CampTypeService() {
		dao = new CampTypeDAO();
	}
	
	public CampTypeVO addCampType(String ct_name) {
		CampTypeVO campTypeVO = new CampTypeVO();
				
		campTypeVO.setCt_name(ct_name);
		dao.insert(campTypeVO);
		
		return campTypeVO;
	}
	
	public CampTypeVO updateCampType(String ct_no, String ct_name) {
		CampTypeVO campTypeVO = new CampTypeVO();
		
		campTypeVO.setCt_no(ct_no);;
		campTypeVO.setCt_name(ct_name);;
		dao.update(campTypeVO);
		
		return campTypeVO;
	}
	
	public void deleteCampType(String ct_no) {
		dao.delete(ct_no);
	}
	
	public CampTypeVO getOneCampType(String ct_no) {
		return dao.findByPrimaryKey(ct_no);
	}
	
	public List<CampTypeVO> getAll() {
		return dao.getAll();
	}
	
}
