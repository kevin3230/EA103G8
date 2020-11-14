package com.vendor.model;

import java.sql.Date;
import java.util.List;

public class VendorService {
	
private VendorDAO_interface dao;
	
	public VendorService() {
		dao = new VendorDAO();
	}
	
	public VendorVO addVendor(String vd_email, String vd_pwd, String vd_name,
			String vd_id, Date vd_birth, String vd_mobile,
			String vd_cgname, String vd_cgtel, String vd_cgaddr,
			String vd_taxid, String vd_acc, Integer vd_stat,
			Integer vd_cgstat, Double vd_lat, Double vd_lon, byte[] vd_brc, byte[] vd_map) {

		VendorVO vendorVO = new VendorVO();

		vendorVO.setVd_email(vd_email);
		vendorVO.setVd_pwd(vd_pwd);
		vendorVO.setVd_name(vd_name);
		vendorVO.setVd_id(vd_id);
		vendorVO.setVd_birth(vd_birth);
		vendorVO.setVd_mobile(vd_mobile);
		vendorVO.setVd_cgname(vd_cgname);
		vendorVO.setVd_cgtel(vd_cgtel);
		vendorVO.setVd_cgaddr(vd_cgaddr);
		vendorVO.setVd_taxid(vd_taxid);
		vendorVO.setVd_acc(vd_acc);
		vendorVO.setVd_regdate(new Date(System.currentTimeMillis()));//瘜冽�ate頧��
		vendorVO.setVd_stat(vd_stat);
		vendorVO.setVd_cgstat(vd_cgstat);
		vendorVO.setVd_lat(vd_lat);
		vendorVO.setVd_lon(vd_lon);
		vendorVO.setVd_brc(vd_brc);
		vendorVO.setVd_brc(vd_map);
		dao.insert(vendorVO);

		return vendorVO;
	}
	
	public VendorVO updateVendor(String vd_no, String vd_email, String vd_pwd,
			String vd_name, String vd_id, Date vd_birth, String vd_mobile,
			String vd_cgname, String vd_cgtel, String vd_cgaddr, String vd_taxid, 
			String vd_acc, Integer vd_stat, Integer vd_cgstat, 
			Double vd_lat, Double vd_lon, byte[] vd_brc) {

		VendorVO vendorVO = new VendorVO();

		vendorVO.setVd_no(vd_no);
		vendorVO.setVd_email(vd_email);
		vendorVO.setVd_pwd(vd_pwd);
		vendorVO.setVd_name(vd_name);
		vendorVO.setVd_id(vd_id);
		vendorVO.setVd_birth(vd_birth);
		vendorVO.setVd_mobile(vd_mobile);
		vendorVO.setVd_cgname(vd_cgname);
		vendorVO.setVd_cgtel(vd_cgtel);
		vendorVO.setVd_cgaddr(vd_cgaddr);
		vendorVO.setVd_taxid(vd_taxid);
		vendorVO.setVd_acc(vd_acc);
		vendorVO.setVd_stat(vd_stat);
		vendorVO.setVd_cgstat(vd_cgstat);
		vendorVO.setVd_lat(vd_lat);
		vendorVO.setVd_lon(vd_lon);
		vendorVO.setVd_brc(vd_brc);
		dao.update(vendorVO);

		return vendorVO;
	}
	
	public VendorVO pswupdateVendor(String vd_no, String vd_pwd) {

		VendorVO vendorVO = new VendorVO();

		vendorVO.setVd_no(vd_no);
		vendorVO.setVd_pwd(vd_pwd);
		dao.pswupdate(vendorVO);

		return vendorVO;
	}
	
	public VendorVO updatenopicVendor(String vd_no, String vd_email, String vd_pwd,
			String vd_name, String vd_id, Date vd_birth, String vd_mobile,
			String vd_cgname, String vd_cgtel, String vd_cgaddr, String vd_taxid, 
			String vd_acc, Integer vd_stat, Integer vd_cgstat, 
			Double vd_lat, Double vd_lon) {

		VendorVO vendorVO = new VendorVO();

		vendorVO.setVd_no(vd_no);
		vendorVO.setVd_email(vd_email);
		vendorVO.setVd_pwd(vd_pwd);
		vendorVO.setVd_name(vd_name);
		vendorVO.setVd_id(vd_id);
		vendorVO.setVd_birth(vd_birth);
		vendorVO.setVd_mobile(vd_mobile);
		vendorVO.setVd_cgname(vd_cgname);
		vendorVO.setVd_cgtel(vd_cgtel);
		vendorVO.setVd_cgaddr(vd_cgaddr);
		vendorVO.setVd_taxid(vd_taxid);
		vendorVO.setVd_acc(vd_acc);
		vendorVO.setVd_stat(vd_stat);
		vendorVO.setVd_cgstat(vd_cgstat);
		vendorVO.setVd_lat(vd_lat);
		vendorVO.setVd_lon(vd_lon);
		dao.updatenopic(vendorVO);

		return vendorVO;
	}
	
	public void deleteVendor(String vd_no) {
		dao.delete(vd_no);
	}

	public VendorVO getOneVendor(String vd_no) {
		return dao.findByPrimaryKey(vd_no);
	}

	public List<VendorVO> getAll() {
		return dao.getAll();
	}
	
	public VendorVO getOneVendornopic(String vd_no) {
		return dao.findByPrimaryKey(vd_no);
	}

	public List<VendorVO> getAllnopic() {
		return dao.getAll();
	}
	public VendorVO signInVendor(String vd_email) {
		return dao.checkByEmail(vd_email);
	}
	
	public VendorVO getVdpic(String vd_no) {
		return dao.findVdpicByPrimaryKey(vd_no);
	}
	
	public VendorVO addfakeInformation(String vd_email, String vd_pwd, String vd_name,
			String vd_id, Date vd_birth, String vd_mobile,
			String vd_cgname, String vd_cgtel, String vd_cgaddr,
			String vd_taxid, String vd_acc, Integer vd_stat,
			Integer vd_cgstat, Double vd_lat, Double vd_lon) {

		VendorVO vendorVO = new VendorVO();

		vendorVO.setVd_email(vd_email);
		vendorVO.setVd_pwd(vd_pwd);
		vendorVO.setVd_name(vd_name);
		vendorVO.setVd_id(vd_id);
		vendorVO.setVd_birth(vd_birth);
		vendorVO.setVd_mobile(vd_mobile);
		vendorVO.setVd_cgname(vd_cgname);
		vendorVO.setVd_cgtel(vd_cgtel);
		vendorVO.setVd_cgaddr(vd_cgaddr);
		vendorVO.setVd_taxid(vd_taxid);
		vendorVO.setVd_acc(vd_acc);
		vendorVO.setVd_regdate(new Date(System.currentTimeMillis()));//瘜冽�ate頧��
		vendorVO.setVd_stat(vd_stat);
		vendorVO.setVd_cgstat(vd_cgstat);
		vendorVO.setVd_lat(vd_lat);
		vendorVO.setVd_lon(vd_lon);
		dao.insert(vendorVO);

		return vendorVO;
	}
	
}
