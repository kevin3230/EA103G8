package com.cgintro.model;

import java.util.List;

public interface CGIntroDAO_interface {
	public void insert(CGIntroVO cgIntroVo);
	public void updateContent(CGIntroVO cgIntroVo);
	public void updateStat(CGIntroVO cgIntroVo);
	public void delete(String cgi_no);
	public CGIntroVO findByPrimaryKey(String cgi_no);
	public List<CGIntroVO> getAll();
	// 用業者編號查詢
	public CGIntroVO getCGIntroByVdno(String vd_no);	// 只取上線版本
	public List<CGIntroVO> getCGIntrosByVdno(String vd_no);
}