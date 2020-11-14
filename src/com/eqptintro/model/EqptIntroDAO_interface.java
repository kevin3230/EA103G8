package com.eqptintro.model;

import java.util.List;

public interface EqptIntroDAO_interface {
	
	public void insert(EqptIntroVO eqptintroVO);
	public void update(EqptIntroVO eqptintroVO);
	public void delete(String ei_no);
	public EqptIntroVO findByPrimaryKey(String ei_no);
	public List<EqptIntroVO> getAll();
	
}
