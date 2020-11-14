package com.equipment.model;

public class EquipmentVO implements java.io.Serializable {
	private String eqpt_no;
	private String eqpt_vdno;
	private String eqpt_name;
	private String eqpt_etno;
	private Integer eqpt_qty;
	private Integer eqpt_price;
	private Integer eqpt_stat;
	private byte[] eqpt_pic;
	
	public EquipmentVO() {
		super();
	}

	public EquipmentVO(String eqpt_no, String eqpt_vdno, String eqpt_name, String eqpt_etno, Integer eqpt_qty,
			Integer eqpt_price, Integer eqpt_stat, byte[] eqpt_pic) {
		super();
		this.eqpt_no = eqpt_no;
		this.eqpt_vdno = eqpt_vdno;
		this.eqpt_name = eqpt_name;
		this.eqpt_etno = eqpt_etno;
		this.eqpt_qty = eqpt_qty;
		this.eqpt_price = eqpt_price;
		this.eqpt_stat = eqpt_stat;
		this.eqpt_pic = eqpt_pic;
	}

	public String getEqpt_no() {
		return eqpt_no;
	}

	public void setEqpt_no(String eqpt_no) {
		this.eqpt_no = eqpt_no;
	}

	public String getEqpt_vdno() {
		return eqpt_vdno;
	}

	public void setEqpt_vdno(String eqpt_vdno) {
		this.eqpt_vdno = eqpt_vdno;
	}

	public String getEqpt_name() {
		return eqpt_name;
	}

	public void setEqpt_name(String eqpt_name) {
		this.eqpt_name = eqpt_name;
	}

	public String getEqpt_etno() {
		return eqpt_etno;
	}

	public void setEqpt_etno(String eqpt_etno) {
		this.eqpt_etno = eqpt_etno;
	}

	public Integer getEqpt_qty() {
		return eqpt_qty;
	}

	public void setEqpt_qty(Integer eqpt_qty) {
		this.eqpt_qty = eqpt_qty;
	}

	public Integer getEqpt_price() {
		return eqpt_price;
	}

	public void setEqpt_price(Integer eqpt_price) {
		this.eqpt_price = eqpt_price;
	}

	public Integer getEqpt_stat() {
		return eqpt_stat;
	}

	public void setEqpt_stat(Integer eqpt_stat) {
		this.eqpt_stat = eqpt_stat;
	}

	public byte[] getEqpt_pic() {
		return eqpt_pic;
	}

	public void setEqpt_pic(byte[] eqpt_pic) {
		this.eqpt_pic = eqpt_pic;
	}

}
