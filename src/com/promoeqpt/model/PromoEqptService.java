package com.promoeqpt.model;

import java.util.List;

public class PromoEqptService {
	private PromoEqptDAO_interface peDAO;

	public PromoEqptService() {
		peDAO = new PromoEqptDAO();
	}

	public void insert(PromoEqptVO peVO) {
		peDAO.insert(peVO);
	}

	public void insert(List<PromoEqptVO> peVOList) {
		peDAO.insert(peVOList);
	}

	public void update(PromoEqptVO peVO) {
		peDAO.update(peVO);
	}

	public List<PromoEqptVO> getByPe_prono(String pe_prono) {
		List<PromoEqptVO> peVOList = peDAO.queryByPro_no(pe_prono);
		return peVOList;
	}
	
	public List<PromoEqptVO> getActiveByPe_eqptno(String pe_eqptno) {
		List<PromoEqptVO> peVOList = peDAO.queryActiveByEqpt_no(pe_eqptno);
		return peVOList;
	}

	public PromoEqptVO getActiveMinPriceByPe_eqptno(String pe_eqptno){
		List<PromoEqptVO> peVOList = peDAO.queryActiveByEqpt_no(pe_eqptno);
		if(peVOList.size() == 0) {
			return null;
		}
		PromoEqptVO peVO = peVOList.get(0);
		int minPrice = peVO.getPe_price();
		for(PromoEqptVO peVOi : peVOList) {
			if(peVOi.getPe_price() < minPrice) {
				minPrice = peVOi.getPe_price();
				peVO = peVOi;
			}
		}
		return peVO;
	}
	
	public void delete(PromoEqptVO peVO) {
		peDAO.delete(peVO);
	}

	public void delete(List<PromoEqptVO> peVOList) {
		peDAO.delete(peVOList);
	}
}
