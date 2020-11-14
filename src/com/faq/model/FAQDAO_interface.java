package com.faq.model;

import java.util.*;

public interface FAQDAO_interface {
	public void insert(FAQVO faqVO);
	public void update(FAQVO faqVO);
	public void delete(String faq_no);
	public FAQVO findByPrimaryKey(String faq_no);
	public List<FAQVO> getAll();
	
	public List<FAQVO> getFAQsByFaqtno(String faqt_no);
	public List<FAQVO> getFAQsByKeyword(String keyword);
}
