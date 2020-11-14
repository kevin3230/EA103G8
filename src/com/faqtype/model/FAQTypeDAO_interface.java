package com.faqtype.model;

import java.util.List;

public interface FAQTypeDAO_interface {
	public void insert(FAQTypeVO faqTypeVO);
	public void update(FAQTypeVO faqTypeVO);
	public void delete(String faqt_no);
	public FAQTypeVO findByPrimaryKey(String faqt_no);
	public List<FAQTypeVO> getAll();
}
