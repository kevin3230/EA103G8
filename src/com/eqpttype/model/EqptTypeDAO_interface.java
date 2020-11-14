package com.eqpttype.model;

import java.util.List;

public interface EqptTypeDAO_interface {
	public void insert(EqptTypeVO eqptTypeVO);
	public void update(EqptTypeVO eqptTypeVO);
	public void delete(String et_no);
	public EqptTypeVO findByPrimaryKey(String et_no);
	public List<EqptTypeVO> getAll();
}
