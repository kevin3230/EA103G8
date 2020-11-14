package com.campavail.model;

import java.sql.Date;

public class CampAvailVO {
	private String ca_campno;
	private Date ca_date;
	private Integer ca_qty;
	
	public String getCa_campno() {
		return ca_campno;
	}
	public void setCa_campno(String ca_campno) {
		this.ca_campno = ca_campno;
	}
	public Date getCa_date() {
		return ca_date;
	}
	public void setCa_date(Date ca_date) {
		this.ca_date = ca_date;
	}
	public Integer getCa_qty() {
		return ca_qty;
	}
	public void setCa_qty(Integer ca_qty) {
		this.ca_qty = ca_qty;
	}
}