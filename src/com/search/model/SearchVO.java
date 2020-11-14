package com.search.model;

import java.io.Serializable;

public class SearchVO implements Serializable {
	private String vd_no;
	private String vd_cgname;
	private String vd_cgaddr;
	private Integer minListPrice;
	private Integer minPrice;

	public Integer getMinListPrice() {
		return minListPrice;
	}
	public void setMinListPrice(Integer minListPrice) {
		this.minListPrice = minListPrice;
	}
	public String getVd_no() {
		return vd_no;
	}
	public void setVd_no(String vd_no) {
		this.vd_no = vd_no;
	}
	public String getVd_cgname() {
		return vd_cgname;
	}
	public void setVd_cgname(String vd_cgname) {
		this.vd_cgname = vd_cgname;
	}
	public String getVd_cgaddr() {
		return vd_cgaddr;
	}
	public void setVd_cgaddr(String vd_cgaddr) {
		this.vd_cgaddr = vd_cgaddr;
	}
	public Integer getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
}