package com.myfav.model;

import java.util.List;

public interface MyfavDAO_interface {
	public void insert(MyfavVO myfavVO);
	public void delete(String myfav_memno, String myfav_vdno);
	public List<MyfavVO> getAll();
	// 用會員編號(mem_no)查詢
	public List<MyfavVO> getMyfavsByMemno(String mem_no);
}
