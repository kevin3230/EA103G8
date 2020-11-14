package com.camp.model;

import java.util.*;

public class CampService {
	
	private CampDAO_interface dao;
	
	public CampService() {
		dao = new CampDAO();
	}
	
	public CampVO addCamp(String camp_vdno, String camp_name, String camp_ctno, Integer camp_qty,
			Integer camp_price, Integer camp_stat, byte[] camp_pic) {

		CampVO campVO = new CampVO();
		
		campVO.setCamp_vdno(camp_vdno);
		campVO.setCamp_name(camp_name);
		campVO.setCamp_ctno(camp_ctno);
		campVO.setCamp_qty(camp_qty);
		campVO.setCamp_price(camp_price);
		campVO.setCamp_stat(camp_stat);
		campVO.setCamp_pic(camp_pic);
		dao.insert(campVO);

		return campVO;
	}
	
	public CampVO updateCamp(String camp_no, String camp_vdno, String camp_name, String camp_ctno, Integer camp_qty,
			Integer camp_price, Integer camp_stat, byte[] camp_pic) {

		CampVO campVO = new CampVO();
		
		campVO.setCamp_no(camp_no);
		campVO.setCamp_vdno(camp_vdno);
		campVO.setCamp_name(camp_name);
		campVO.setCamp_ctno(camp_ctno);
		campVO.setCamp_qty(camp_qty);
		campVO.setCamp_price(camp_price);
		campVO.setCamp_stat(camp_stat);
		campVO.setCamp_pic(camp_pic);
		dao.update(campVO);

		return campVO;
	}
	
	public void deleteCamp(String camp_no) {
		dao.delete(camp_no);
	}
	
	public CampVO getOneCamp(String camp_no) {
		return dao.findByPrimaryKey(camp_no);
	}
	
	public List<CampVO> getAll() {
		return dao.getAll();
	}
	
	// Add by Yen-Fu Chen
	public List<CampVO> getCampsByVdno(String vd_no) {
		return dao.getCampsByVdno(vd_no);
	}
	
	// 把狀態為「-1 刪除」者篩選掉
	public List<CampVO> getCampsByVdnoWithoutDeleted(String vd_no) {
		List<CampVO> list = dao.getCampsByVdno(vd_no);
		List<CampVO> withoutDeleted = new ArrayList<CampVO>();
		for (CampVO campVO : list) {
			if (campVO.getCamp_stat() != -1)  {
				withoutDeleted.add(campVO);
			}
		}
		return withoutDeleted;
	}
	
	// 多筆上架
	public void updateCampStatTo2(String[] stringArray) {
		for(String camp_no : stringArray) {
			dao.updateCampStatTo2(camp_no);
		}		
	}
	
	// 多筆下架
		public void updateCampStatTo1(String[] stringArray) {
			for(String camp_no : stringArray) {
				dao.updateCampStatTo1(camp_no);
			}		
		}
	
	// 多筆刪除
	public void deleteSelected (String[] stringArray) {
		for(String camp_no : stringArray) {
			dao.delete(camp_no);
		}
	}
	
	public Set<String> getAllCt_no(String vd_no) {
		return dao.getAllCt_no(vd_no);
	}
	
	public List<CampVO> getCampsByVdnoCtno(String vd_no, String ct_no) {
		return dao.getCampsByVdnoCtno(vd_no, ct_no);
	}
	
	// Author Jeff
	public List<CampVO> getAllActiveCamps(){
		List<CampVO> result = new ArrayList<CampVO>();
		for(CampVO campVOi : dao.getAll()) {
			if(campVOi.getCamp_stat() >= 0) {
				result.add(campVOi);
			}
		}
		return result;
	}
	
	// Author Jeff
	public List<CampVO> getExistCampsByVdno(String vd_no){
		return dao.getExistCampsByVdno(vd_no);
	}
	
}