package com.vendor.model;

import java.util.*;

public interface VendorDAO_interface {
	
	public void insert(VendorVO VendorVO);
	public void update(VendorVO VendorVO);
	public void pswupdate(VendorVO VendorVO);
	public void updatenopic(VendorVO VendorVO);
	public void delete(String vd_no);
	public VendorVO findByPrimaryKey(String vd_no);
	public List<VendorVO> getAllnopic();
	public VendorVO findByPrimaryKeynopic(String vd_no);
	public List<VendorVO> getAll();
	public VendorVO checkByEmail(String vd_email);
	public VendorVO findVdpicByPrimaryKey(String vd_no);
	public void fakeInformationinsert(VendorVO VendorVO);
}
