package com.equipment.model;

import java.util.*;

import com.camp.model.CampVO;

public interface EquipmentDAO_interface {
	public void insert(EquipmentVO equipmentVO);
	public void update(EquipmentVO equipmentVO);
	public void delete(String eqpt_no);
	public EquipmentVO findByPrimaryKey(String eqpt_no);
	public List<EquipmentVO> getAll();
	
	public List<EquipmentVO> getEquipmentsByVdno(String vd_no);
	
	// 多筆上架
	public void updateEqptStatTo2(String eqpt_no);
	// 多筆下架
	public void updateEqptStatTo1(String eqpt_no);
		
	//by 柏誼
	public void updatEqptQty(EquipmentVO equipmentVO);
	
	public Set<String> getAllEt_no(String vd_no);
	public List<EquipmentVO> getEquipmentsByVdnoEtno(String vd_no, String et_no);
}
