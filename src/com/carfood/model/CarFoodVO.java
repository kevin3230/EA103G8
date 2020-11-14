package com.carfood.model;

public class CarFoodVO implements java.io.Serializable {
	private String cf_foodno;
	private String cf_memno;
	private Integer cf_qty;

	public CarFoodVO() {
	}

	public CarFoodVO(String cf_foodno, String cf_memno, Integer cf_qty) {
		this.cf_foodno = cf_foodno;
		this.cf_memno = cf_memno;
		this.cf_qty = cf_qty;
	}

	public String getCf_foodno() {
		return cf_foodno;
	}

	public void setCf_foodno(String cf_foodno) {
		this.cf_foodno = cf_foodno;
	}

	public String getCf_memno() {
		return cf_memno;
	}

	public void setCf_memno(String cf_memno) {
		this.cf_memno = cf_memno;
	}

	public Integer getCf_qty() {
		return cf_qty;
	}

	public void setCf_qty(Integer cf_qty) {
		this.cf_qty = cf_qty;
	}

}
