package com.orderfood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderFoodService {

	private OrderFoodDAO_interface dao;
	
	public OrderFoodService() {
		dao = new OrderFoodDAO();
	}
	
	public OrderFoodVO addOrderFood(String of_foodno,
			String of_omno, Integer of_qty, Integer of_listprice,
			Integer of_price, Integer of_stat) {

		OrderFoodVO orderfoodVO = new OrderFoodVO();

		orderfoodVO.setOf_foodno(of_foodno);
		orderfoodVO.setOf_omno(of_omno);
		orderfoodVO.setOf_qty(of_qty);
		orderfoodVO.setOf_listprice(of_listprice);
		orderfoodVO.setOf_price(of_price);
		dao.insert(orderfoodVO);

		return orderfoodVO;
	}
	
	public OrderFoodVO updateOrderFood(String of_foodno,
			String of_omno, Integer of_qty,
			Integer of_stat, String of_no) {

		OrderFoodVO orderfoodVO = new OrderFoodVO();
		orderfoodVO.setOf_foodno(of_foodno);
		orderfoodVO.setOf_omno(of_omno);
		orderfoodVO.setOf_qty(of_qty);
		orderfoodVO.setOf_stat(of_stat);
		orderfoodVO.setOf_no(of_no);
		dao.update(orderfoodVO);

		return orderfoodVO;
	}
	
	public void deleteOrderFood(String of_no) {
		dao.delete(of_no);
	}

	public OrderFoodVO getOneOrderFood(String of_no) {
		return dao.findByPrimaryKey(of_no);
	}

	public List<OrderFoodVO> getAll() {
		return dao.getAll();
	}
	
	// Author Jeff
	public List<OrderFoodVO> getOrderFoodsByOmno(String om_no) {
		return dao.getOrderFoodsByOmno(om_no);
	}
	
	// Author Jeff
	public List<OrderFoodVO> getActiveOrderFoodsByOmno(String om_no) {
		List<OrderFoodVO> result = new ArrayList<OrderFoodVO>();
		for(OrderFoodVO ofVO : dao.getOrderFoodsByOmno(om_no)) {
			if(ofVO.getOf_stat() == 1) {
				result.add(ofVO);
			}
		}
		return result;
	}
	
	//新增by李承璋
	public List<Map<String, String>> getFoodStatic(String vd_no,java.sql.Timestamp oc_start,java.sql.Timestamp oc_end){
		return dao.getFoodStatic(vd_no, oc_start, oc_end);
	}
	//新增by李承璋
}
