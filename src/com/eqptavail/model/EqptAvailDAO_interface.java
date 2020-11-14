package com.eqptavail.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface EqptAvailDAO_interface {
	void add(EqptAvailVO eqptAvailVO);

	void updata(EqptAvailVO eqptAvailVO);

	void delete(String ea_eqptno, Date ea_date);

	EqptAvailVO findByPrimaryKey(String ea_eqptno, Date ea_date);// 使用表個資料型別來取得一筆資料

	List<EqptAvailVO> getAll(); // 使用List來儲存所有表格資料

	List<EqptAvailVO> getAllByEqptno(String ea_eqptno);
	
	// Author Jeff
	void insert(EqptAvailVO eqptAvailVO, Connection con);
	void update(EqptAvailVO eqptAvailVO, Connection con);
}
