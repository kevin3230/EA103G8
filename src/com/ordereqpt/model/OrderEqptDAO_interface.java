package com.ordereqpt.model;

import java.sql.Connection;
import java.util.List;

public interface OrderEqptDAO_interface {
	void add(OrderEqptVO orderEqptVO);

	void updata(OrderEqptVO orderEqptVO);

	void delete(String oe_no);

	OrderEqptVO findByPrimaryKey(String oe_no);// 使用表個資料型別來取得一筆資料

	List<OrderEqptVO> getAll(); // 使用List來儲存所有表格資料
	
	//Author Jeff
	public void insert(OrderEqptVO orderEqptVO, Connection con);
	
	public List<OrderEqptVO> getOrderEqptsByOmno(String om_no);
	
	public List<OrderEqptVO> getActiveOrderEqptsByOmno(String om_no);
	
	public void update(OrderEqptVO orderEqptVO, Connection con);

	public void updatagettime(OrderEqptVO orderEqptVO);

	public void updatabacktime(OrderEqptVO orderEqptVO);
	
	public void delete(OrderEqptVO orderEqptVO, Connection con);
}
