package com.eqptintro.model;

import java.sql.Date;

public class EqptIntroVO implements java.io.Serializable {

	private String ei_no;
	private String ei_adminisno;
	private String ei_title;
	private String ei_content;
	private Date ei_edit;
	private Integer ei_stat;
	
	public String getEi_no() {
		return ei_no;
	}
	public void setEi_no(String ei_no) {
		this.ei_no = ei_no;
	}
	public String getEi_adminisno() {
		return ei_adminisno;
	}
	public void setEi_adminisno(String ei_adminisno) {
		this.ei_adminisno = ei_adminisno;
	}
	public String getEi_title() {
		return ei_title;
	}
	public void setEi_title(String ei_title) {
		this.ei_title = ei_title;
	}
	public String getEi_content() {
		return ei_content;
	}
	public void setEi_content(String ei_content) {
		this.ei_content = ei_content;
	}
	public Date getEi_edit() {
		return ei_edit;
	}
	public void setEi_edit(Date ei_edit) {
		this.ei_edit = ei_edit;
	}
	public Integer getEi_stat() {
		return ei_stat;
	}
	public void setEi_stat(Integer ei_stat) {
		this.ei_stat = ei_stat;
	}
	
}
