package com.careqpt.model;

import java.io.Serializable;
import java.sql.Date;

//此類別對應到資料庫表格
public class CarEqptVO2 implements Serializable {

	// UID可以不加入，因為資料庫表格在建立時應該就定義了相關的規定，不會隨意的變動。
	private static final long serialVersionUID = -8770310171743531778L;

	// 實體變數對應到部們表格的各個欄位
	private String cc_campno;
	private String cc_memno;
	private String camp_vdno;
	private String eqpt_no;
	private Date cc_start;
	private Date cc_end;
	private String eqpt_name;
	private String eqpt_etno;
	private Integer eqpt_price;
	private Integer eqpt_stat;
	private Integer eqpt_qty;
	private String ea_eqptno;
	private Date ea_date;
	private Integer ea_qty;
	private byte[] eqpt_pic;
	private String pro_no;
	private Date pro_start;
	private Date pro_end;
	private String pro_vdno;
	private int pro_stat;
	private String pe_eqptno;
	private int pe_price;
	
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
	public String getCamp_vdno() {
		return camp_vdno;
	}
	public void setCamp_vdno(String camp_vdno) {
		this.camp_vdno = camp_vdno;
	}
	public String getEqpt_no() {
		return eqpt_no;
	}
	public void setEqpt_no(String eqpt_no) {
		this.eqpt_no = eqpt_no;
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
	public Integer getEqpt_qty() {
		return eqpt_qty;
	}
	public void setEqpt_qty(Integer eqpt_qty) {
		this.eqpt_qty = eqpt_qty;
	}
	public String getEa_eqptno() {
		return ea_eqptno;
	}
	public void setEa_eqptno(String ea_eqptno) {
		this.ea_eqptno = ea_eqptno;
	}
	public Date getEa_date() {
		return ea_date;
	}
	public void setEa_date(Date ea_date) {
		this.ea_date = ea_date;
	}
	public Integer getEa_qty() {
		return ea_qty;
	}
	public void setEa_qty(Integer ea_qty) {
		this.ea_qty = ea_qty;
	}
	public byte[] getEqpt_pic() {
		return eqpt_pic;
	}
	public void setEqpt_pic(byte[] eqpt_pic) {
		this.eqpt_pic = eqpt_pic;
	}
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
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
	public String getPe_eqptno() {
		return pe_eqptno;
	}
	public void setPe_eqptno(String pe_eqptno) {
		this.pe_eqptno = pe_eqptno;
	}
	public int getPe_price() {
		return pe_price;
	}
	public void setPe_price(int pe_price) {
		this.pe_price = pe_price;
	}
	
	
	
}
