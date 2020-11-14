package com.cgintro.model;

import java.util.List;

public class CGIntroService {
	private CGIntroDAO_interface dao;
	
	public CGIntroService() {
		dao = new CGIntroDAO();
	}
	
	public CGIntroVO addCGIntro(String cgi_vdno, String cgi_content) {
		CGIntroVO cgIntroVO = new CGIntroVO();
		
		cgIntroVO.setCgi_vdno(cgi_vdno);
		cgIntroVO.setCgi_content(cgi_content);
		dao.insert(cgIntroVO);
		
		return cgIntroVO;
	}
	
	public CGIntroVO updateCGIntroContent(String cgi_no, String cgi_content) {
		CGIntroVO cgIntroVO = new CGIntroVO();
		
		cgIntroVO.setCgi_no(cgi_no);
		cgIntroVO.setCgi_content(cgi_content);
		dao.updateContent(cgIntroVO);
		
		return cgIntroVO;
	}
	
	public CGIntroVO updateCGIntroStat(String cgi_no, Integer cgi_stat) {
		CGIntroVO cgIntroVO = new CGIntroVO();
		
		cgIntroVO.setCgi_no(cgi_no);
		cgIntroVO.setCgi_stat(cgi_stat);
		dao.updateStat(cgIntroVO);
		
		return cgIntroVO;
	}
	
	public void deleteCGIntro(String cgi_no) {
		dao.delete(cgi_no);
	}
	
	public CGIntroVO getOneCGIntro(String cgi_no) {
		return dao.findByPrimaryKey(cgi_no);
	}
	
	public List<CGIntroVO> getAll() {
		return dao.getAll();
	}

	public CGIntroVO getCGIntroByVdno(String vd_no) {
		return dao.getCGIntroByVdno(vd_no);
	}
	
	public List<CGIntroVO> getCGIntrosByVdno(String vd_no) {
		return dao.getCGIntrosByVdno(vd_no);
	}
}