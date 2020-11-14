package com.camp.model;

public class CampVO implements java.io.Serializable {
	private String camp_no;
	private String camp_vdno;
	private String camp_name;
	private String camp_ctno;
	private Integer camp_qty;
	private Integer camp_price;
	private Integer camp_stat;
	private byte[] camp_pic;
	
	public CampVO() {
		super();
	}

	public CampVO(String camp_no, String camp_vdno, String camp_name, String camp_ctno, Integer camp_qty,
			Integer camp_price, Integer camp_stat, byte[] camp_pic) {
		super();
		this.camp_no = camp_no;
		this.camp_vdno = camp_vdno;
		this.camp_name = camp_name;
		this.camp_ctno = camp_ctno;
		this.camp_qty = camp_qty;
		this.camp_price = camp_price;
		this.camp_stat = camp_stat;
		this.camp_pic = camp_pic;
	}

	public String getCamp_no() {
		return camp_no;
	}

	public void setCamp_no(String camp_no) {
		this.camp_no = camp_no;
	}

	public String getCamp_vdno() {
		return camp_vdno;
	}

	public void setCamp_vdno(String camp_vdno) {
		this.camp_vdno = camp_vdno;
	}

	public String getCamp_name() {
		return camp_name;
	}

	public void setCamp_name(String camp_name) {
		this.camp_name = camp_name;
	}

	public String getCamp_ctno() {
		return camp_ctno;
	}

	public void setCamp_ctno(String camp_ctno) {
		this.camp_ctno = camp_ctno;
	}

	public Integer getCamp_qty() {
		return camp_qty;
	}

	public void setCamp_qty(Integer camp_qty) {
		this.camp_qty = camp_qty;
	}

	public Integer getCamp_price() {
		return camp_price;
	}

	public void setCamp_price(Integer camp_price) {
		this.camp_price = camp_price;
	}

	public Integer getCamp_stat() {
		return camp_stat;
	}

	public void setCamp_stat(Integer camp_stat) {
		this.camp_stat = camp_stat;
	}

	public byte[] getCamp_pic() {
		return camp_pic;
	}

	public void setCamp_pic(byte[] camp_pic) {
		this.camp_pic = camp_pic;
	}
	
}
