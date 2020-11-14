package com.adminis.model;

import java.sql.Date;
import java.util.List;

public class AdminisService {

	private AdminisDAO_interface dao;

	public AdminisService() {
		dao = new AdminisDAO();
	}

	public AdminisVO addAdminis( String adminis_name, String adminis_pwd, String adminis_email
							   , String adminis_dept, Integer adminis_pv) {

		AdminisVO adminisVO = new AdminisVO();

		adminisVO.setAdminis_name(adminis_name);
		adminisVO.setAdminis_pwd(adminis_pwd);
		adminisVO.setAdminis_email(adminis_email);
		adminisVO.setAdminis_dept(adminis_dept);
		adminisVO.setAdminis_pv(adminis_pv);
		dao.add(adminisVO);

		return adminisVO;
	}

	public AdminisVO updateAdminis(String adminis_no, String adminis_name, String adminis_pwd, 
			String adminis_email, String adminis_dept, Integer adminis_pv) {

		AdminisVO adminisVO = new AdminisVO();
		
		adminisVO.setAdminis_no(adminis_no);
		adminisVO.setAdminis_name(adminis_name);
		adminisVO.setAdminis_pwd(adminis_pwd);
		adminisVO.setAdminis_email(adminis_email);
		adminisVO.setAdminis_dept(adminis_dept);
		adminisVO.setAdminis_pv(adminis_pv);
		dao.updata(adminisVO);

		return adminisVO;
	}
	public void swapAdminis(String adminis_no, Integer adminis_stat) {
		dao.swap(adminis_no, adminis_stat);
	}
	
	public void deleteAdminis(String adminis_no) {
		dao.delete(adminis_no);
	}

	public AdminisVO getOneAdminis(String adminis_no) {
		return dao.findByPrimaryKey(adminis_no);
	}
	public List<AdminisVO> getAdminisStat(Integer adminis_stat) {
		return dao.findByAdminis_stat(adminis_stat);
	}
	
	public List<AdminisVO> getAll() {
		return dao.getAll();
	}
}
