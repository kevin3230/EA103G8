package com.careqpt.model;

import java.io.Serializable;
import java.sql.Date;

//此類別對應到資料庫表格
public class CarEqptVO implements Serializable {

	// UID可以不加入，因為資料庫表格在建立時應該就定義了相關的規定，不會隨意的變動。
	private static final long serialVersionUID = -8770310171743531778L;

	// 實體變數對應到部們表格的各個欄位
	private String ce_no;
	private String ce_eqptno;
	private String ce_memno;
	private Integer ce_qty;
	private Date ce_expget; // Author: Jeff
	private Date ce_expback; // Author: Jeff

	public CarEqptVO() {}
	
	public CarEqptVO(String ce_eqptno, String ce_memno, Integer ce_qty, Date ce_expget, Date ce_expback) {
		this.ce_eqptno = ce_eqptno;
		this.ce_memno = ce_memno;
		this.ce_qty = ce_qty;
		this.ce_expget = ce_expget;
		this.ce_expback = ce_expback;
	}

	public String getCe_no() {
		return ce_no;
	}

	public void setCe_no(String ce_no) {
		this.ce_no = ce_no;
	}

	public String getCe_eqptno() {
		return ce_eqptno;
	}

	public void setCe_eqptno(String ce_eqptno) {
		this.ce_eqptno = ce_eqptno;
	}

	public String getCe_memno() {
		return ce_memno;
	}

	public void setCe_memno(String ce_memno) {
		this.ce_memno = ce_memno;
	}

	public Integer getCe_qty() {
		return ce_qty;
	}

	public void setCe_qty(Integer ce_qty) {
		this.ce_qty = ce_qty;
	}

	public Date getCe_expget() {
		return ce_expget;
	}

	public void setCe_expget(Date ce_expget) {
		this.ce_expget = ce_expget;
	}

	public Date getCe_expback() {
		return ce_expback;
	}

	public void setCe_expback(Date ce_expback) {
		this.ce_expback = ce_expback;
	}

}
