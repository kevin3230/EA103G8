package com.faqtype.model;

import java.util.List;

public class FAQTypeService {
	
	private FAQTypeDAO_interface dao;
	
	public FAQTypeService() {
		dao = new FAQTypeDAO();
	}
	
	public FAQTypeVO addFAQType(String faqt_name) {
		FAQTypeVO faqTypeVO = new FAQTypeVO();
		
		faqTypeVO.setFaqt_name(faqt_name);
		dao.insert(faqTypeVO);
		
		return faqTypeVO;
	}
	
	public FAQTypeVO updateFAQType(String faqt_no, String faqt_name) {
		FAQTypeVO faqTypeVO = new FAQTypeVO();
		
		faqTypeVO.setFaqt_no(faqt_no);
		faqTypeVO.setFaqt_name(faqt_name);
		dao.update(faqTypeVO);
		
		return faqTypeVO;
	}
	
	public void deleteFAQType(String faqt_no) {
		dao.delete(faqt_no);
	}
	
	public FAQTypeVO getOneFAQType(String faqt_no) {
		return dao.findByPrimaryKey(faqt_no);
	}
	
	public List<FAQTypeVO> getAll() {
		return dao.getAll();
	}
	
}
