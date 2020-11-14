package com.vendor.model;

import java.util.List;

public class VendorLeaseEqptService {
	
private VendorLeaseEqptDAO_interface dao;
	
	public VendorLeaseEqptService() {
		dao = new VendorLeaseEqptDAO();
	}

	public List<VendorLeaseEqptVO> getAllByToday(String om_vdno) {
		return dao.getAllByToday(om_vdno);
	}
	
	public List<VendorLeaseEqptVO> getOrderEqptsByOmno(String om_no) {
		return dao.getOrderEqptsByOmno(om_no);
	}
	
}
