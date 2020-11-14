package com.promotion.model;

import java.util.List;

import com.promocamp.model.PromoCampVO;
import com.promoeqpt.model.PromoEqptVO;
import com.promofood.model.PromoFoodVO;

public interface PromotionDAO_interface {

	public String insert(PromotionVO proVO);

	public String insert(PromotionVO proVO, List<PromoCampVO> pcVOList, List<PromoEqptVO> peVOList,
			List<PromoFoodVO> pfVOList);

	public void update(PromotionVO proVO);

	public PromotionVO queryByNo(String pro_no);

	public List<PromotionVO> queryAll();

	public void delete(String pro_no);
	
	public void delete(String[] pro_noArr);
	
	public void delete(PromotionVO proVO);

	public void delete(List<PromotionVO> proVOList);
	
	// Add by Yen-Fu Chen
	public List<PromotionVO> getAlivePromosByVdno(String vd_no);
	
	public List<PromotionVO> getAllByVdno(String vd_no);
}