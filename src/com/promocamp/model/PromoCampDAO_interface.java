package com.promocamp.model;

import java.sql.Connection;
import java.util.List;

public interface PromoCampDAO_interface {
	public void insert(PromoCampVO pcVO);

	public void insert(List<PromoCampVO> pcVOList);

	public void insert(List<PromoCampVO> pcVOList, Connection con);

	public void update(PromoCampVO pcVO);

	public List<PromoCampVO> queryByPro_no(String pc_prono);
	
	public List<PromoCampVO> queryActiveByCamp_no(String pc_campno);
	
	// Add by Yen-Fu Chen
	public List<PromoCampVO> queryAliveByCamp_no(String pc_campno);

	public void delete(PromoCampVO pcVO);

	public void delete(List<PromoCampVO> pcVOList);
}
