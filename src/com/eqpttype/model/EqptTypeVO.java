package com.eqpttype.model;

public class EqptTypeVO {
	String et_no;
	String et_name;
	
	public EqptTypeVO() {
		super();
	}
	public EqptTypeVO(String et_no, String et_name) {
		super();
		this.et_no = et_no;
		this.et_name = et_name;
	}
	public String getEt_no() {
		return et_no;
	}
	public void setEt_no(String et_no) {
		this.et_no = et_no;
	}
	public String getEt_name() {
		return et_name;
	}
	public void setEt_name(String et_name) {
		this.et_name = et_name;
	}
	
}
