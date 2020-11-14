package com.vendor.model;

import java.util.List;

public interface VendorLeaseEqptDAO_interface {


	List<VendorLeaseEqptVO> getAllByToday(String om_vdno);

	List<VendorLeaseEqptVO> getOrderEqptsByOmno(String om_no);

}
