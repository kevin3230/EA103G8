package com.promotion.model;

import java.sql.Date;

public class PromotionVO {
	private String pro_no;
	private String pro_name;
	private Date pro_start;
	private Date pro_end;
	private String pro_vdno;
	private int pro_stat;
	
	public PromotionVO() {}
	
	public PromotionVO(String pro_no, String pro_name, Date pro_start, Date pro_end, String pro_vdno, int pro_stat) {
		this.setPro_no(pro_no);
		this.setPro_name(pro_name);
		this.setPro_start(pro_start);
		this.setPro_end(pro_end);
		this.setPro_vdno(pro_vdno);
		this.setPro_stat(pro_stat);
	}

	public String getPro_no() {
		return pro_no;
	}

	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public Date getPro_start() {
		return pro_start;
	}

	public void setPro_start(Date pro_start) {
		this.pro_start = pro_start;
	}

	public Date getPro_end() {
		return pro_end;
	}

	public void setPro_end(Date pro_end) {
		this.pro_end = pro_end;
	}

	public String getPro_vdno() {
		return pro_vdno;
	}

	public void setPro_vdno(String pro_vdno) {
		this.pro_vdno = pro_vdno;
	}

	public int getPro_stat() {
		return pro_stat;
	}

	public void setPro_stat(int pro_stat) {
		this.pro_stat = pro_stat;
	}
	
}