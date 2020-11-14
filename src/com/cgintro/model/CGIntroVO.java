package com.cgintro.model;

import java.sql.Timestamp;

public class CGIntroVO {
	private String cgi_no;
	private String cgi_vdno;
	private String cgi_content;
	private Timestamp cgi_edit;
	private Integer cgi_stat;
	
	public Integer getCgi_stat() {
		return cgi_stat;
	}
	public void setCgi_stat(Integer cgi_stat) {
		this.cgi_stat = cgi_stat;
	}
	public String getCgi_no() {
		return cgi_no;
	}
	public void setCgi_no(String cgi_no) {
		this.cgi_no = cgi_no;
	}
	public String getCgi_vdno() {
		return cgi_vdno;
	}
	public void setCgi_vdno(String cgi_vdno) {
		this.cgi_vdno = cgi_vdno;
	}
	public String getCgi_content() {
		return cgi_content;
	}
	public void setCgi_content(String cgi_content) {
		this.cgi_content = cgi_content;
	}
	public Timestamp getCgi_edit() {
		return cgi_edit;
	}
	public void setCgi_edit(Timestamp cgi_edit) {
		this.cgi_edit = cgi_edit;
	}
}