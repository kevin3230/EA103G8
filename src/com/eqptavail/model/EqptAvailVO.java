package com.eqptavail.model;

import java.io.Serializable;
import java.sql.Date;

//此類別對應到資料庫表格
public class EqptAvailVO implements Serializable {

	// UID可以不加入，因為資料庫表格在建立時應該就定義了相關的規定，不會隨意的變動。
	private static final long serialVersionUID = -3016689291082155127L;

	// 實體變數對應到部們表格的各個欄位
	private String ea_eqptno;
	private Date ea_date;
	private Integer ea_qty;

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

}
