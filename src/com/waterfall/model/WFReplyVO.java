package com.waterfall.model;

import java.sql.Date;

public class WFReplyVO implements java.io.Serializable {
	private String wfr_no;
	private String wfr_wfno;
	private String wfr_memno;
	private String wfr_content;
	private String wfr_edit;
	private Integer wfr_stat;
	
	public String getWfr_no() {
		return wfr_no;
	}
	public void setWfr_no(String wfr_no) {
		this.wfr_no = wfr_no;
	}
	public String getWfr_wfno() {
		return wfr_wfno;
	}
	public void setWfr_wfno(String wfr_wfno) {
		this.wfr_wfno = wfr_wfno;
	}
	public String getWfr_memno() {
		return wfr_memno;
	}
	public void setWfr_memno(String wfr_memno) {
		this.wfr_memno = wfr_memno;
	}
	public String getWfr_content() {
		return wfr_content;
	}
	public void setWfr_content(String wfr_content) {
		this.wfr_content = wfr_content;
	}
	public String getWfr_edit() {
		return wfr_edit;
	}
	public void setWfr_edit(String wfr_edit) {
		this.wfr_edit = wfr_edit;
	}
	public Integer getWfr_stat() {
		return wfr_stat;
	}
	public void setWfr_stat(Integer wfr_stat) {
		this.wfr_stat = wfr_stat;
	}
	

}
