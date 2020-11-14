package com.ordermaster.model;

import java.sql.Connection;
import java.util.*;

import com.carcamp.model.CarCampVO;
import com.careqpt.model.CarEqptVO;
import com.carfood.model.CarFoodVO;
import com.ordercamp.model.OrderCampVO;
import com.ordereqpt.model.OrderEqptVO;
import com.orderfood.model.OrderFoodVO;

public interface OrderMasterDAO_interface {
	public void insert(OrderMasterVO OrderMasterVO);

	public void update(OrderMasterVO OrderMasterVO);

	public void delete(String om_no);

	public OrderMasterVO findByPrimaryKey(String om_no);

	public List<OrderMasterVO> getAll();

	// Author Jeff
	public String insertWithDetails(OrderMasterVO omVO, List<OrderCampVO> ocVOList, List<OrderEqptVO> oeVOList,
			List<OrderFoodVO> ofVOList, List<CarCampVO> ccVOList, List<CarEqptVO> ceVOList, List<CarFoodVO> cfVOList);

	public String updateWithDetails(OrderMasterVO omVO, List<OrderCampVO> ocVOList, List<OrderEqptVO> oeVOList,
			List<OrderFoodVO> ofVOList);

	public Map<String, Integer> recoverCampAvailFromOrderMaster(String om_no, Connection conn);
	public Map<String, Integer> recoverEqptAvailFromOrderMaster(String om_no, Connection conn);
	
	//新增by李承璋
	public List<OrderMasterVO> getByMemno(String om_memno);
	public List<OrderMasterVO> getByMemno(Map<String, String[]> map);
	public void cancelOrder(String om_no);
	//新增by李承璋
}
