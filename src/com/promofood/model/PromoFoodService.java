package com.promofood.model;

import java.util.List;

public class PromoFoodService {
	private PromoFoodDAO_interface pfDAO;

	public PromoFoodService() {
		pfDAO = new PromoFoodDAO();
	}

	public void insert(PromoFoodVO pfVO) {
		pfDAO.insert(pfVO);
	}

	public void insert(List<PromoFoodVO> pfVOList) {
		pfDAO.insert(pfVOList);
	}

	public void update(PromoFoodVO pfVO) {
		pfDAO.update(pfVO);
	}

	public List<PromoFoodVO> getByPf_prono(String pf_prono) {
		List<PromoFoodVO> pfVOList = pfDAO.queryByPro_no(pf_prono);
		return pfVOList;
	}
	
	public List<PromoFoodVO> getActiveByPf_foodno(String pf_foodno) {
		List<PromoFoodVO> pfVOList = pfDAO.queryActiveByFood_no(pf_foodno);
		return pfVOList;
	}

	public PromoFoodVO getActiveMinPriceByPf_foodno(String pf_foodno){
		List<PromoFoodVO> pfVOList = pfDAO.queryActiveByFood_no(pf_foodno);
		if(pfVOList.size() == 0) {
			return null;
		}
		PromoFoodVO pfVO = pfVOList.get(0);
		int minPrice = pfVO.getPf_price();
		for(PromoFoodVO pfVOi : pfVOList) {
			if(pfVOi.getPf_price() < minPrice) {
				minPrice = pfVOi.getPf_price();
				pfVO = pfVOi;
			}
		}
		return pfVO;
	}
	
	public void delete(PromoFoodVO pfVO) {
		pfDAO.delete(pfVO);
	}

	public void delete(List<PromoFoodVO> pfVOList) {
		pfDAO.delete(pfVOList);
	}
	
	//新增by李承璋
	public PromoFoodVO getActiveLowPriceByPf_foodno(String pf_foodno) {
		PromoFoodVO pfVO = pfDAO.queryActiveLowPriceByFood_no(pf_foodno);
		return pfVO;
	}
	//新增by李承璋
}
