package com.authority.model;

import java.util.List;

public interface AuthorityDAO_interface {
	void add(AuthorityVO authorityVO); 
	void updata(AuthorityVO authorityVO);
	void delete(Integer auth_no);
	AuthorityVO findByPrimaryKey(Integer auth_no);//使用表個資料型別來取得一筆資料
	List<AuthorityVO> getAll(); //使用List來儲存所有表格資料
}
