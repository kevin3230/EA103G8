package com.faqtype.model;

public class FAQTypeVO implements java.io.Serializable {
	String faqt_no;
	String faqt_name;
	
	public FAQTypeVO() {
		super();
	}

	public FAQTypeVO(String faqt_no, String faqt_name) {
		super();
		this.faqt_no = faqt_no;
		this.faqt_name = faqt_name;
	}

	public String getFaqt_no() {
		return faqt_no;
	}

	public void setFaqt_no(String faqt_no) {
		this.faqt_no = faqt_no;
	}

	public String getFaqt_name() {
		return faqt_name;
	}

	public void setFaqt_name(String faqt_name) {
		this.faqt_name = faqt_name;
	}

}
