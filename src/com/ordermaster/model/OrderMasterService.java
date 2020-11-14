package com.ordermaster.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.carcamp.model.CarCampVO;
import com.careqpt.model.CarEqptVO;
import com.carfood.model.CarFoodVO;
import com.ordercamp.model.OrderCampVO;
import com.ordereqpt.model.OrderEqptVO;
import com.orderfood.model.OrderFoodVO;

public class OrderMasterService {

	private OrderMasterDAO_interface dao;

	public OrderMasterService() {
		dao = new OrderMasterDAO();
	}

	public OrderMasterVO addOrderMaster(String om_no, String om_memno, String om_vdno, Integer om_stat, String om_note,
			Integer om_txnamt, String om_cardno) {

		OrderMasterVO ordermasterVO = new OrderMasterVO();

		ordermasterVO.setOm_no(om_no);
		ordermasterVO.setOm_memno(om_memno);
		ordermasterVO.setOm_vdno(om_vdno);
		ordermasterVO.setOm_stat(om_stat);
		ordermasterVO.setOm_note(om_note);
		ordermasterVO.setOm_estbl(new Timestamp(System.currentTimeMillis()));// 注意Date轉換
		ordermasterVO.setOm_txnamt(om_txnamt);
		ordermasterVO.setOm_txntime(new Date(System.currentTimeMillis()));// 注意Date轉換
		ordermasterVO.setOm_cardno(om_cardno);
		dao.insert(ordermasterVO);

		return ordermasterVO;
	}

	public OrderMasterVO updateOrderMaster(Integer om_stat, String om_note, Date om_txntime, String om_cardno) {

		OrderMasterVO ordermasterVO = new OrderMasterVO();

		ordermasterVO.setOm_stat(om_stat);
		ordermasterVO.setOm_note(om_note);
		ordermasterVO.setOm_txntime(om_txntime);
		ordermasterVO.setOm_cardno(om_cardno);
		dao.update(ordermasterVO);

		return ordermasterVO;
	}

	public void deleteOrderMaster(String om_no) {
		dao.delete(om_no);
	}

	public OrderMasterVO getOneOrderMaster(String om_no) {
		return dao.findByPrimaryKey(om_no);
	}

	public List<OrderMasterVO> getAll() {
		return dao.getAll();
	}

	// Author Jeff
	public String insertWithDetails(OrderMasterVO omVO, List<OrderCampVO> ocVOList, List<OrderEqptVO> oeVOList,
			List<OrderFoodVO> ofVOList, List<CarCampVO> ccVOList, List<CarEqptVO> ceVOList, List<CarFoodVO> cfVOList) {
		return dao.insertWithDetails(omVO, ocVOList, oeVOList, ofVOList, ccVOList, ceVOList, cfVOList);
	}
	
	// Author Jeff
	public String updateWithDetails(OrderMasterVO omVO, List<OrderCampVO> ocVOList, List<OrderEqptVO> oeVOList,
			List<OrderFoodVO> ofVOList) {
		return dao.updateWithDetails(omVO, ocVOList, oeVOList, ofVOList);
	}

	//新增by李承璋
	public List<OrderMasterVO> getByMemno(String om_memno) {
		return dao.getByMemno(om_memno);
	}
	public List<OrderMasterVO> getByMemno(Map<String, String[]> map) {
		return dao.getByMemno(map);
	}
	public void cancelOrder(String om_no) {
		dao.cancelOrder(om_no);
	}
	//新增by李承璋
}
