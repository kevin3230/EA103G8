package com.carcamp.model;

import java.io.Serializable;
import java.sql.Date;

public class CarCampVO implements Serializable {
	private String cc_campno;
	private String cc_memno;
	private Integer cc_qty;
	private Date cc_start;
	private Date cc_end;
	
	public CarCampVO() {}
	
	public CarCampVO(String cc_campno, String cc_memno, Integer cc_qty, Date cc_start, Date cc_end) {
		this.cc_campno = cc_campno;
		this.cc_memno = cc_memno;
		this.cc_qty = cc_qty;
		this.cc_start = cc_start;
		this.cc_end = cc_end;
	}
	
	public String getCc_campno() {
		return cc_campno;
	}
	public void setCc_campno(String cc_campno) {
		this.cc_campno = cc_campno;
	}
	public String getCc_memno() {
		return cc_memno;
	}
	public void setCc_memno(String cc_memno) {
		this.cc_memno = cc_memno;
	}
	public Integer getCc_qty() {
		return cc_qty;
	}
	public void setCc_qty(Integer cc_qty) {
		this.cc_qty = cc_qty;
	}
	public Date getCc_start() {
		return cc_start;
	}
	public void setCc_start(Date cc_start) {
		this.cc_start = cc_start;
	}
	public Date getCc_end() {
		return cc_end;
	}
	public void setCc_end(Date cc_end) {
		this.cc_end = cc_end;
	}
}