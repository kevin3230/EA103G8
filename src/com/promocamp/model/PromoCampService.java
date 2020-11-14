package com.promocamp.model;

import java.util.List;

import com.promotion.model.*;

public class PromoCampService {
	private PromoCampDAO_interface pcDAO;
	private PromotionDAO_interface proDAO;

	public PromoCampService() {
		pcDAO = new PromoCampDAO();
		proDAO = new PromotionDAO();
	}

	public void insert(PromoCampVO pcVO) {
		pcDAO.insert(pcVO);
	}

	public void insert(List<PromoCampVO> pcVOList) {
		pcDAO.insert(pcVOList);
	}

	public void update(PromoCampVO pcVO) {
		pcDAO.update(pcVO);
	}

	public List<PromoCampVO> getByPc_prono(String pc_prono) {
		List<PromoCampVO> pcVOList = pcDAO.queryByPro_no(pc_prono);
		return pcVOList;
	}
	
	public List<PromoCampVO> getActiveByPc_campno(String pc_campno) {
		List<PromoCampVO> pcVOList = pcDAO.queryActiveByCamp_no(pc_campno);
		return pcVOList;
	}
	
	// Add by Yen-Fu Chen
	public List<PromoCampVO> getAliveByCampno(String pc_campno) {
		List<PromoCampVO> pcVOList = pcDAO.queryActiveByCamp_no(pc_campno);
		return pcVOList;
	}
	
	public PromoCampVO getActiveMinPriceByPc_campno(String pc_campno){
		List<PromoCampVO> pcVOList = pcDAO.queryActiveByCamp_no(pc_campno);
		if(pcVOList.size() == 0) {
			return null;
		}
		PromoCampVO pcVO = pcVOList.get(0);
		int minPrice = pcVO.getPc_price();
		for(PromoCampVO pcVOi : pcVOList) {
			if(pcVOi.getPc_price() < minPrice) {
				minPrice = pcVOi.getPc_price();
				pcVO = pcVOi;
			}
		}
		return pcVO;
	}

	public void delete(PromoCampVO pcVO) {
		pcDAO.delete(pcVO);
	}

	public void delete(List<PromoCampVO> pcVOList) {
		pcDAO.delete(pcVOList);
	}
}
