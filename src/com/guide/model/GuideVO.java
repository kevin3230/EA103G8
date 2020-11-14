package com.guide.model;

import java.sql.Date;

public class GuideVO {
	private String guide_no;
	private String guide_title;
	private String guide_content;
	private Date guide_edit;
	private int guide_stat;
	private String guide_adminisno;
	
	public GuideVO() {}
	
	public GuideVO(String guide_no, String guide_title, String guide_content, Date guide_edit, int guide_stat, String guide_adminisno) {
		this.setGuide_no(guide_no);
		this.setGuide_title(guide_title);
		this.setGuide_content(guide_content);
		this.setGuide_edit(guide_edit);
		this.setGuide_stat(guide_stat);
		this.setGuide_adminisno(guide_adminisno);
	}
	
	public String getGuide_no() {
		return guide_no;
	}
	public void setGuide_no(String guide_no) {
		this.guide_no = guide_no;
	}
	public String getGuide_title() {
		return guide_title;
	}
	public void setGuide_title(String guide_title) {
		this.guide_title = guide_title;
	}
	public String getGuide_content() {
		return guide_content;
	}
	public void setGuide_content(String guide_content) {
		this.guide_content = guide_content;
	}
	public Date getGuide_edit() {
		return guide_edit;
	}
	public void setGuide_edit(Date guide_edit) {
		this.guide_edit = guide_edit;
	}
	public int getGuide_stat() {
		return guide_stat;
	}
	public void setGuide_stat(int guide_stat) {
		this.guide_stat = guide_stat;
	}
	public String getGuide_adminisno() {
		return guide_adminisno;
	}
	public void setGuide_adminisno(String guide_adminisno) {
		this.guide_adminisno = guide_adminisno;
	}
	
	
}
