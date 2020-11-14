package com.cgintro.model;

import java.util.List;

public interface CGIPicDAO_interface {
	public void insert(CGIPicVO cgiPicVO);
	public void update(CGIPicVO cgiPicVO);
	public void delete(String cgip_no);
	public CGIPicVO findByPrimaryKey(String cgip_no);
	public List<CGIPicVO> getAll();
	// 用露營區介紹編號查詢
	public List<CGIPicVO> getCGIPicsByCgino(String cgi_no);
}