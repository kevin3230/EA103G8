package com.promofood.model;

import java.sql.Connection;
import java.util.List;

public interface PromoFoodDAO_interface {
	public void insert(PromoFoodVO pfVO);

	public void insert(List<PromoFoodVO> pfVOList);

	public void insert(List<PromoFoodVO> pfVOList, Connection con);

	public void update(PromoFoodVO pfVO);

	public List<PromoFoodVO> queryByPro_no(String pf_prono);
	
	public List<PromoFoodVO> queryActiveByFood_no(String pf_foodno);

	public void delete(PromoFoodVO pfVO);

	public void delete(List<PromoFoodVO> pfVOList);
	
	//新增by李承璋
	public PromoFoodVO queryActiveLowPriceByFood_no(String pf_foodno);
	//新增by李承璋
}
