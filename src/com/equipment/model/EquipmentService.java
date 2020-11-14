package com.equipment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EquipmentService {

	private EquipmentDAO_interface dao;
	
	public EquipmentService() {
		dao = new EquipmentDAO();
	}
	
	public EquipmentVO addEquipment(String eqpt_vdno, String eqpt_name, String eqpt_etno, Integer eqpt_qty, Integer eqpt_price,
			Integer eqpt_stat, byte[] eqpt_pic) {

		EquipmentVO equipmentVO = new EquipmentVO();
		
		equipmentVO.setEqpt_vdno(eqpt_vdno);
		equipmentVO.setEqpt_name(eqpt_name);
		equipmentVO.setEqpt_etno(eqpt_etno);
		equipmentVO.setEqpt_qty(eqpt_qty);
		equipmentVO.setEqpt_price(eqpt_price);
		equipmentVO.setEqpt_stat(eqpt_stat);			
		equipmentVO.setEqpt_pic(eqpt_pic);
		dao.insert(equipmentVO);
		
		return equipmentVO;
	}
	
	public EquipmentVO updateEquipment(String eqpt_no, String eqpt_vdno, String eqpt_name, String eqpt_etno, Integer eqpt_qty, Integer eqpt_price,
			Integer eqpt_stat, byte[] eqpt_pic) {

		EquipmentVO equipmentVO = new EquipmentVO();
		
		equipmentVO.setEqpt_no(eqpt_no);
		equipmentVO.setEqpt_vdno(eqpt_vdno);
		equipmentVO.setEqpt_name(eqpt_name);
		equipmentVO.setEqpt_etno(eqpt_etno);
		equipmentVO.setEqpt_qty(eqpt_qty);
		equipmentVO.setEqpt_price(eqpt_price);
		equipmentVO.setEqpt_stat(eqpt_stat);			
		equipmentVO.setEqpt_pic(eqpt_pic);
		dao.update(equipmentVO);

		return equipmentVO;
	}
	
	public void deleteEquipment(String eqpt_no) {
		dao.delete(eqpt_no);
	}
	
	public EquipmentVO getOneEquipment(String eqpt_no) {
		return dao.findByPrimaryKey(eqpt_no);
	}
	
	public List<EquipmentVO> getAll() {
		return dao.getAll();
	}
	
	public List<EquipmentVO> getEquipmentsByVdno(String vd_no) {
		return dao.getEquipmentsByVdno(vd_no);
	}
	
	// 把狀態為「-1 刪除」者篩選掉
	public List<EquipmentVO> getEquipmentsByVdnoWithoutDeleted(String vd_no) {
		List<EquipmentVO> list = dao.getEquipmentsByVdno(vd_no);
		List<EquipmentVO> withoutDeleted = new ArrayList<EquipmentVO>();
		for (EquipmentVO equipmentVO : list) {
			if (equipmentVO.getEqpt_stat() != -1)  {
				withoutDeleted.add(equipmentVO);
			}
		}
		return withoutDeleted;
	}
	
	// 多筆上架
	public void updateEqptStatTo2(String[] stringArray) {
		for(String camp_no : stringArray) {
			dao.updateEqptStatTo2(camp_no);
			}		
	}
	
	// 多筆下架
		public void updateEqptStatTo1(String[] stringArray) {
			for(String camp_no : stringArray) {
				dao.updateEqptStatTo1(camp_no);
				}		
		}
		
	// 多筆刪除
	public void deleteSelected (String[] stringArray) {
		for(String camp_no : stringArray) {
			dao.delete(camp_no);
		}
	}
	
	public EquipmentVO updatEqptQty(String eqpt_no,Integer eqpt_qty) {
		
		EquipmentVO equipmentVO = new EquipmentVO();
		
		equipmentVO.setEqpt_no(eqpt_no);;
		equipmentVO.setEqpt_qty(eqpt_qty);;
		dao.updatEqptQty(equipmentVO);

		return equipmentVO;
	}
	
	// Author Jeff
	public List<EquipmentVO> getExistEqptsByVdno(String vd_no) {
		List<EquipmentVO> result = new ArrayList<EquipmentVO>();
		for(EquipmentVO eqptVOi : dao.getEquipmentsByVdno(vd_no)) {
			if(eqptVOi.getEqpt_stat() >= 0) {
				result.add(eqptVOi);
			}
		}
		return result;
	}
	public Set<String> getAllEt_no(String vd_no) {
		return dao.getAllEt_no(vd_no);
	}
	
	public List<EquipmentVO> getEquipmentsByVdnoEtno(String vd_no, String et_no) {
		return dao.getEquipmentsByVdnoEtno(vd_no, et_no);
	}
	
}
