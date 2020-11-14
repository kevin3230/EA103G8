package com.orderfood.model;

import java.sql.Connection;
import java.util.*;

public interface OrderFoodDAO_interface {
	public void insert(OrderFoodVO OrderFoodVO);

	public void update(OrderFoodVO OrderFoodVO);

	public void delete(String of_no);

	public OrderFoodVO findByPrimaryKey(String of_no);

	public List<OrderFoodVO> getAll();

	// Author Jeff
	public void insert(OrderFoodVO orderFoodVO, Connection con);

	// Author Jeff
	public void update(OrderFoodVO orderFoodVO, Connection con);
	
	// Author Jeff
	public void delete(OrderFoodVO orderFoodVO, Connection con);

	public List<OrderFoodVO> getOrderFoodsByOmno(String om_no);
	//新增by李承璋
	public List<Map<String, String>> getFoodStatic(String vd_no, java.sql.Timestamp oc_start, java.sql.Timestamp oc_end);
	//新增by李承璋
}
