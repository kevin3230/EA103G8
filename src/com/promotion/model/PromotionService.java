package com.promotion.model;

import java.util.List;
import java.util.Map;

import com.promocamp.model.PromoCampVO;
import com.promoeqpt.model.PromoEqptVO;
import com.promofood.model.PromoFoodVO;

public class PromotionService {
	private PromotionDAO_interface proDAO;

	public PromotionService() {
		proDAO = new PromotionDAO();
	}

	public String insert(PromotionVO proVO) {
		String pro_no = proDAO.insert(proVO);
		return pro_no;
	}

	public String insertWithItems(PromotionVO proVO, List<PromoCampVO> pcVOList, List<PromoEqptVO> peVOList,
			List<PromoFoodVO> pfVOList) {
		String pro_no = proDAO.insert(proVO, pcVOList, peVOList, pfVOList);
		return pro_no;
	}

	public void update(PromotionVO proVO) {
		proDAO.update(proVO);
	}

	public PromotionVO getOnePro(String pro_no) {
		PromotionVO proVO = proDAO.queryByNo(pro_no);
		return proVO;
	}

	public List<PromotionVO> getAllPro() {
		List<PromotionVO> proVOList = proDAO.queryAll();
		return proVOList;
	}

	public void delete(String pro_no) {
		proDAO.delete(pro_no);
	}

	public void delete(PromotionVO proVO) {
		proDAO.delete(proVO);
	}

	public void delete(List<PromotionVO> proVOList) {
		proDAO.delete(proVOList);
	}
	
	// Add by Yen-Fu Chen
	public List<PromotionVO> getAlivePromosByVdno(String vd_no) {
		return proDAO.getAlivePromosByVdno(vd_no);
	}
	
	public void getAlivePromosByVdno(String vd_no, Map<String, PromotionVO> map) {
		List<PromotionVO> list = proDAO.getAlivePromosByVdno(vd_no);
		for (PromotionVO promoVO : list) {
			map.put(promoVO.getPro_no(), promoVO);
		}
	}
	//==========================================
	
	public List<PromotionVO> getAllByVdno(String vd_no) {
		return proDAO.getAllByVdno(vd_no);
	}
}