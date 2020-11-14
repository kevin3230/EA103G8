package com.adminis.model;

import java.util.List;

public interface AdminisDAO_interface {
	void add(AdminisVO adminisVO);

	void updata(AdminisVO adminisVO);

	void delete(String adminis_no);

	void swap(String adminis_no, Integer adminis_stat);
	
	AdminisVO findByPrimaryKey(String adminis_no);
	
	List<AdminisVO> findByAdminis_stat(Integer adminis_stat);
	
	List<AdminisVO> getAll();
	}
