package com.ordercamp.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderCampService {
	private OrderCampDAO_interface dao;
	
	public OrderCampService() {
		dao = new OrderCampDAO();
	}
	
	public OrderCampVO addOrderCamp(String oc_campno, String oc_omno, Integer oc_qty,
			Integer oc_listprice, Integer oc_price, Date oc_start, Date oc_end) {
		OrderCampVO orderCampVO = new OrderCampVO();
		
		orderCampVO.setOc_campno(oc_campno);
		orderCampVO.setOc_omno(oc_omno);
		orderCampVO.setOc_qty(oc_qty);
		orderCampVO.setOc_listprice(oc_listprice);
		orderCampVO.setOc_price(oc_price);
		orderCampVO.setOc_start(oc_start);
		orderCampVO.setOc_end(oc_end);
		dao.insert(orderCampVO);
		
		return orderCampVO;
	}
	
	public OrderCampVO updateOrderCamp(String oc_no, Integer oc_stat) {
		OrderCampVO orderCampVO = new OrderCampVO();
		
		orderCampVO.setOc_no(oc_no);
		orderCampVO.setOc_stat(oc_stat);
		dao.update(orderCampVO);
		
		return orderCampVO;
	}
	
	public OrderCampVO getOneOrderCamp(String oc_no) {
		return dao.findByPrimaryKey(oc_no);
	}
	
	public List<OrderCampVO> getAll() {
		return dao.getAll();
	}
	
	public List<OrderCampVO> getOrderCampsByCampno(String camp_no) {
		return dao.getOrderCampsByCampno(camp_no);
	}
	
	public List<OrderCampVO> getOrderCampsByOmno(String om_no) {
		return dao.getOrderCampsByOmno(om_no);
	}
	
	// Author Jeff
	public List<OrderCampVO> getActiveOrderCampsByOmno(String om_no) {
		return dao.getActiveOrderCampsByOmno(om_no);
	}
	
}