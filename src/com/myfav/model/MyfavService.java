package com.myfav.model;

import java.util.List;

public class MyfavService {
	private MyfavDAO_interface dao;
	
	public MyfavService() {
		dao = new MyfavDAO();
	}
	
	public MyfavVO addMyfav(String myfav_memno, String myfav_vdno) {
		MyfavVO myfavVO = new MyfavVO();
		
		myfavVO.setMyfav_memno(myfav_memno);
		myfavVO.setMyfav_vdno(myfav_vdno);
		dao.insert(myfavVO);
		
		return myfavVO;
	}
	
	public void deleteMyfav(String myfav_memno, String myfav_vdno) {
		dao.delete(myfav_memno, myfav_vdno);
	}
	
	public List<MyfavVO> getAll() {
		return dao.getAll();
	}
	
	public List<MyfavVO> getMyfavsByMemno(String mem_no) {
		return dao.getMyfavsByMemno(mem_no);
	}
}