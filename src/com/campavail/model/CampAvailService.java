package com.campavail.model;

import java.sql.Date;
import java.util.List;

public class CampAvailService {
	private CampAvailDAO_interface dao;
	
	public CampAvailService() {
		dao = new CampAvailDAO();
	}
	
	public CampAvailVO addCampAvail(String ca_campno, Date ca_date, Integer ca_qty) {
		CampAvailVO campAvailVO = new CampAvailVO();
				
		campAvailVO.setCa_campno(ca_campno);
		campAvailVO.setCa_date(ca_date);
		campAvailVO.setCa_qty(ca_qty);
		dao.insert(campAvailVO);
		
		return campAvailVO;
	}
	
	public CampAvailVO updateCampAvail(String ca_campno, Date ca_date, Integer ca_qty) {
		CampAvailVO campAvailVO = new CampAvailVO();
		
		campAvailVO.setCa_campno(ca_campno);
		campAvailVO.setCa_date(ca_date);
		campAvailVO.setCa_qty(ca_qty);
		dao.update(campAvailVO);
		
		return campAvailVO;
	}
	
	public void deleteCampAvail(String ca_campno, Date ca_date) {
		dao.delete(ca_campno, ca_date);
	}
	
	public CampAvailVO getOneCampAvail(String ca_campno, Date ca_date) {
		return dao.findByPrimaryKey(ca_campno, ca_date);
	}
	
	public List<CampAvailVO> getAll() {
		return dao.getAll();
	}
	
	public List<CampAvailVO> getCampAvailsByCampno(String camp_no, Date start, Date end) {
		return dao.getCampAvailsByCampno(camp_no, start, end);
	}
}