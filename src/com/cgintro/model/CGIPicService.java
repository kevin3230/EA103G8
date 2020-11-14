package com.cgintro.model;

import java.util.List;

public class CGIPicService {
	private CGIPicDAO_interface dao;
	
	public CGIPicService() {
		dao = new CGIPicDAO();
	}
	
	public CGIPicVO addCGIPic(String cgip_cgino, byte[] cgip_pic) {
		CGIPicVO cgiPicVO = new CGIPicVO();
		
		cgiPicVO.setCgip_cgino(cgip_cgino);
		cgiPicVO.setCgip_pic(cgip_pic);
		dao.insert(cgiPicVO);
		
		return cgiPicVO;
	}
	
	public CGIPicVO updateCGIPic(String cgip_no, byte[] cgip_pic) {
		CGIPicVO cgiPicVO = new CGIPicVO();
		
		cgiPicVO.setCgip_no(cgip_no);
		cgiPicVO.setCgip_pic(cgip_pic);
		dao.update(cgiPicVO);
		
		return cgiPicVO;
	}
	
	public void deleteCGIPic(String cgip_no) {
		dao.delete(cgip_no);
	}
	
	public CGIPicVO getOneCGIPic(String cgip_no) {
		return dao.findByPrimaryKey(cgip_no);
	}
	
	public List<CGIPicVO> getAll() {
		return dao.getAll();
	}
	
	public List<CGIPicVO> getCGIPicsByCgino(String cgi_no) {
		return dao.getCGIPicsByCgino(cgi_no);
	}
}