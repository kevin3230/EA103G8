package com.camptype.model;

public class CampTypeVO {
	String ct_no;
	String ct_name;
	
	public CampTypeVO() {
		super();
	}
	
	public CampTypeVO(String ct_no, String ct_name) {
		super();
		this.ct_no = ct_no;
		this.ct_name = ct_name;
	}

	public String getCt_no() {
		return ct_no;
	}
	public void setCt_no(String ct_no) {
		this.ct_no = ct_no;
	}
	public String getCt_name() {
		return ct_name;
	}
	public void setCt_name(String ct_name) {
		this.ct_name = ct_name;
	}
}