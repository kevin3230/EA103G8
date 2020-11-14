package com.cnotice.model;

import java.sql.Date;

public class CNoticeVO {
	
	private String cn_no;
	private String cn_memno;
	private String cn_omno;
	private String cn_content;
	private Date cn_time;
	private String cn_type;
	private Integer cn_stat;

	public String getCn_no() {
		return cn_no;
	}
	public void setCn_no(String cn_no) {
		this.cn_no = cn_no;
	}
	public String getCn_memno() {
		return cn_memno;
	}
	public void setCn_memno(String cn_memno) {
		this.cn_memno = cn_memno;
	}
	public String getCn_omno() {
		return cn_omno;
	}
	public void setCn_omno(String cn_omno) {
		this.cn_omno = cn_omno;
	}
	public String getCn_content() {
		return cn_content;
	}
	public void setCn_content(String cn_content) {
		this.cn_content = cn_content;
	}
	public Date getCn_time() {
		return cn_time;
	}
	public void setCn_time(Date cn_time) {
		this.cn_time = cn_time;
	}
	public String getCn_type() {
		return cn_type;
	}
	public void setCn_type(String cn_type) {
		this.cn_type = cn_type;
	}
	public Integer getCn_stat() {
		return cn_stat;
	}
	public void setCn_stat(Integer cn_stat) {
		this.cn_stat = cn_stat;
	}
}
