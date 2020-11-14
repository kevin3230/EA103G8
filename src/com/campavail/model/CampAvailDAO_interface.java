package com.campavail.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface CampAvailDAO_interface {
	public void insert(CampAvailVO campAvailVO);
	public void update(CampAvailVO campAvailVO);
	public void delete(String ca_campno, Date ca_date);
	public CampAvailVO findByPrimaryKey(String ca_campno, Date ca_date);
	public List<CampAvailVO> getAll();
	// 用營位編號(camp_no)和特定日期區段(date)查詢
	public List<CampAvailVO> getCampAvailsByCampno(String camp_no, Date start, Date end);
	
	// Author Jeff
	public void insert(CampAvailVO campAvailVO, Connection con);
	public void update(CampAvailVO campAvailVO, Connection con);
}