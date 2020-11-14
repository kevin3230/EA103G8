package com.vnotice.model;

import java.sql.Date;

public class VNoticeVO {

	private String vn_no;
	private String vn_vdno;
	private String vn_omno;
	private String vn_content;
	private Date vn_time;
	private String vn_type;
	private Integer vn_stat;
	
	public String getVn_no() {
		return vn_no;
	}
	public void setVn_no(String vn_no) {
		this.vn_no = vn_no;
	}
	public String getVn_vdno() {
		return vn_vdno;
	}
	public void setVn_vdno(String vn_vdno) {
		this.vn_vdno = vn_vdno;
	}
	public String getVn_omno() {
		return vn_omno;
	}
	public void setVn_omno(String vn_omno) {
		this.vn_omno = vn_omno;
	}
	public String getVn_content() {
		return vn_content;
	}
	public void setVn_content(String vn_content) {
		this.vn_content = vn_content;
	}
	public Date getVn_time() {
		return vn_time;
	}
	public void setVn_time(Date vn_time) {
		this.vn_time = vn_time;
	}
	public String getVn_type() {
		return vn_type;
	}
	public void setVn_type(String vn_type) {
		this.vn_type = vn_type;
	}
	public Integer getVn_stat() {
		return vn_stat;
	}
	public void setVn_stat(Integer vn_stat) {
		this.vn_stat = vn_stat;
	}
}
