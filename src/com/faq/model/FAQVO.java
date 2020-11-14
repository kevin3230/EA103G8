package com.faq.model;

import java.sql.Date;

public class FAQVO implements java.io.Serializable {
	private String faq_no;
	private String faq_faqtno;
	private String faq_title;
	private String faq_content;
	private Date faq_edit;
	private Integer faq_stat;
	private String faq_adminisno;
	
	public FAQVO() {
		super();
	}

	public FAQVO(String faq_no, String faq_faqtno, String faq_title, String faq_content, Date faq_edit, Integer faq_stat,
			String faq_adminisno) {
		super();
		this.faq_no = faq_no;
		this.faq_faqtno = faq_faqtno;
		this.faq_title = faq_title;
		this.faq_content = faq_content;
		this.faq_edit = faq_edit;
		this.faq_stat = faq_stat;
		this.faq_adminisno = faq_adminisno;
	}

	public String getFaq_no() {
		return faq_no;
	}

	public void setFaq_no(String faq_no) {
		this.faq_no = faq_no;
	}
	
	public String getFaq_faqtno() {
		return faq_faqtno;
	}

	public void setFaq_faqtno(String faq_faqtno) {
		this.faq_faqtno = faq_faqtno;
	}

	public String getFaq_title() {
		return faq_title;
	}

	public void setFaq_title(String faq_title) {
		this.faq_title = faq_title;
	}

	public String getFaq_content() {
		return faq_content;
	}

	public void setFaq_content(String faq_content) {
		this.faq_content = faq_content;
	}

	public Date getFaq_edit() {
		return faq_edit;
	}

	public void setFaq_edit(Date faq_edit) {
		this.faq_edit = faq_edit;
	}

	public Integer getFaq_stat() {
		return faq_stat;
	}

	public void setFaq_stat(Integer faq_stat) {
		this.faq_stat = faq_stat;
	}

	public String getFaq_adminisno() {
		return faq_adminisno;
	}

	public void setFaq_adminisno(String faq_adminisno) {
		this.faq_adminisno = faq_adminisno;
	}
	
}
