package com.ordereqpt.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderEqptService {

	private OrderEqptDAO_interface dao;

	public OrderEqptService() {
		dao = new OrderEqptDAO();
	}

	// Edit by Yen-Fu Chen
	public OrderEqptVO addOrderEqpt(String oe_eqptno, String oe_omno, Integer oe_qty, Integer oe_listprice,
			Integer oe_price, Date oe_expget, Date oe_expback) {
		OrderEqptVO orderEqptVO = new OrderEqptVO();

		orderEqptVO.setOe_eqptno(oe_eqptno);
		orderEqptVO.setOe_omno(oe_omno);
		orderEqptVO.setOe_qty(oe_qty);
		orderEqptVO.setOe_listprice(oe_listprice);
		orderEqptVO.setOe_price(oe_price);
		orderEqptVO.setOe_expget(oe_expget);
		orderEqptVO.setOe_expback(oe_expback);

		dao.add(orderEqptVO);

		return orderEqptVO;
	}
	
	// Edit by Yen-Fu Chen
	public OrderEqptVO updateOrderEqpt(String oe_no, Integer oe_stat) {
		OrderEqptVO orderEqptVO = new OrderEqptVO();
		
		orderEqptVO.setOe_no(oe_no);
		orderEqptVO.setOe_stat(oe_stat);

		dao.updata(orderEqptVO);

		return orderEqptVO;
	}

	public void deleteOrderEqpt(String oe_no) {
		dao.delete(oe_no);
	}

	public OrderEqptVO getOneEmp(String oe_no) {
		return dao.findByPrimaryKey(oe_no);
	}

	public List<OrderEqptVO> getAll() {
		return dao.getAll();
	}
	
	// Author Jeff
	public List<OrderEqptVO> getOrderEqptsByOmno(String om_no){
		return dao.getOrderEqptsByOmno(om_no);
	}
	
	// Author Jeff
	public List<OrderEqptVO> getActiveOrderEqptsByOmno(String om_no) {
		return dao.getActiveOrderEqptsByOmno(om_no);
	}
	
	public OrderEqptVO updatagettime(String oe_no) {
		
		OrderEqptVO orderEqptVO = new OrderEqptVO();
		
		orderEqptVO.setOe_no(oe_no);

		dao.updatagettime(orderEqptVO);

		return orderEqptVO;
	}
	
	public OrderEqptVO updatabacktime(String oe_no,Integer oe_reqty) {
		
		OrderEqptVO orderEqptVO = new OrderEqptVO();
		
		orderEqptVO.setOe_no(oe_no);
		orderEqptVO.setOe_reqty(oe_reqty);
		dao.updatabacktime(orderEqptVO);

		return orderEqptVO;
	}
	
	
}
