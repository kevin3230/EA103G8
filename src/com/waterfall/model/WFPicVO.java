package com.waterfall.model;

import java.sql.Blob;



public class WFPicVO implements java.io.Serializable {

	private String wfp_no;
	private String wfp_wfno;
	private byte[] wfp_pic;
	
	public String getWfp_no() {
		return wfp_no;
	}
	public void setWfp_no(String wfp_no) {
		this.wfp_no = wfp_no;
	}
	public String getWfp_wfno() {
		return wfp_wfno;
	}
	public void setWfp_wfno(String wfp_wfno) {
		this.wfp_wfno = wfp_wfno;
	}
	public byte[] getWfp_pic() {
		return wfp_pic;
	}
	public void setWfp_pic(byte[] wfp_pic) {
		this.wfp_pic = wfp_pic;
	}
	
}
