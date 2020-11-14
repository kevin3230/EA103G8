package com.faq.model;

import java.sql.Date;
import java.util.List;

public class FAQService {
	
	private FAQDAO_interface dao;

	public FAQService() {
		dao = new FAQDAO();
	}
	
	public FAQVO addFAQ(String faq_title, String faq_content, Date faq_edit, Integer faq_stat,
			String faq_adminisno) {

		FAQVO faqVO = new FAQVO();
		
		faqVO.setFaq_title(faq_title);
		faqVO.setFaq_content(faq_content);
		faqVO.setFaq_edit(faq_edit);
		faqVO.setFaq_stat(faq_stat);			
		faqVO.setFaq_adminisno(faq_adminisno);
		dao.insert(faqVO);
		
		return faqVO;
	}
	
	public FAQVO updateFAQ(String faq_no, String faq_title, String faq_content, Date faq_edit, Integer faq_stat,
			String faq_adminisno) {

		FAQVO faqVO = new FAQVO();
		
		faqVO.setFaq_no(faq_no);
		faqVO.setFaq_title(faq_title);
		faqVO.setFaq_content(faq_content);
		faqVO.setFaq_edit(faq_edit);
		faqVO.setFaq_stat(faq_stat);			
		faqVO.setFaq_adminisno(faq_adminisno);
		dao.insert(faqVO);
		
		return faqVO;
	}
	
	public void deleteFAQ(String faq_no) {
		dao.delete(faq_no);
	}
	
	public FAQVO getOneFAQ(String faq_no) {
		return dao.findByPrimaryKey(faq_no);
	}
	
	public List<FAQVO> getAll() {
		return dao.getAll();
	}
	
	public List<FAQVO> getFAQsByFaqtno(String faqt_no) {
		return dao.getFAQsByFaqtno(faqt_no);
	}
	
	public List<FAQVO> getFAQsByKeyword(String keyword) {
		return dao.getFAQsByKeyword(keyword);
	}
	
}
