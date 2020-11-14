package com.promoeqpt.model;

import java.sql.Connection;
import java.util.List;

import com.promocamp.model.PromoCampVO;

public interface PromoEqptDAO_interface {
	public void insert(PromoEqptVO peVO);

	public void insert(List<PromoEqptVO> peVOList);

	public void insert(List<PromoEqptVO> peVOList, Connection con);

	public void update(PromoEqptVO peVO);

	public List<PromoEqptVO> queryByPro_no(String pe_prono);
	
	public List<PromoEqptVO> queryActiveByEqpt_no(String pe_eqptno);

	public void delete(PromoEqptVO peVO);

	public void delete(List<PromoEqptVO> peVOList);
}
