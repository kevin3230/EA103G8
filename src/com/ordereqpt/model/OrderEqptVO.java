package com.ordereqpt.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

//此類別對應到資料庫表格
public class OrderEqptVO implements Serializable {

	// UID可以不加入，因為資料庫表格在建立時應該就定義了相關的規定，不會隨意的變動。
	private static final long serialVersionUID = -8770310171743531778L;

	// 實體變數對應到部們表格的各個欄位
	private String oe_no;
	private String oe_eqptno;
	private String oe_omno;
	private Integer oe_qty;
	private Integer oe_listprice;
	private Integer oe_price;
	private Integer oe_stat;
	private Date oe_expget;
	private Date oe_expback;
	private Timestamp oe_get;
	private Timestamp oe_back;
	private Integer oe_reqty;
	private Timestamp oe_change;
	private Timestamp oe_estbl;

	// Author Jeff
	public OrderEqptVO() {
	}

	// Author Jeff
	public OrderEqptVO(String oe_eqptno, int oe_qty, int oe_listprice, int oe_price, Date oe_expget, Date oe_expback) {
		/*
		 * This constructor used for OrderMasterJDBCDAO testing for OrderMaster
		 * insertWithDetails method.
		 */
		this.setOe_eqptno(oe_eqptno);
		this.setOe_qty(oe_qty);
		this.setOe_listprice(oe_listprice);
		this.setOe_price(oe_price);
		this.setOe_expget(oe_expget);
		this.setOe_expback(oe_expback);
	}
	
	// Author Jeff
	public OrderEqptVO(String om_no, String oe_eqptno, int oe_qty, int oe_listprice, int oe_price, Date oe_expget, Date oe_expback) {
		/*
		 * This constructor used for OrderMasterJDBCDAO testing for OrderMaster
		 * updateWithDetails method.
		 */
		this.setOe_omno(om_no);
		this.setOe_eqptno(oe_eqptno);
		this.setOe_qty(oe_qty);
		this.setOe_listprice(oe_listprice);
		this.setOe_price(oe_price);
		this.setOe_expget(oe_expget);
		this.setOe_expback(oe_expback);
	}

	public String getOe_no() {
		return oe_no;
	}

	public void setOe_no(String oe_no) {
		this.oe_no = oe_no;
	}

	public String getOe_eqptno() {
		return oe_eqptno;
	}

	public void setOe_eqptno(String oe_eqptno) {
		this.oe_eqptno = oe_eqptno;
	}

	public String getOe_omno() {
		return oe_omno;
	}

	public void setOe_omno(String oe_omno) {
		this.oe_omno = oe_omno;
	}

	public Integer getOe_qty() {
		return oe_qty;
	}

	public void setOe_qty(Integer oe_qty) {
		this.oe_qty = oe_qty;
	}

	public Integer getOe_listprice() {
		return oe_listprice;
	}

	public void setOe_listprice(Integer oe_listprice) {
		this.oe_listprice = oe_listprice;
	}

	public Integer getOe_price() {
		return oe_price;
	}

	public void setOe_price(Integer oe_price) {
		this.oe_price = oe_price;
	}

	public Integer getOe_stat() {
		return oe_stat;
	}

	public void setOe_stat(Integer oe_stat) {
		this.oe_stat = oe_stat;
	}

	public Date getOe_expget() {
		return oe_expget;
	}

	public void setOe_expget(Date oe_expget) {
		this.oe_expget = oe_expget;
	}

	public Date getOe_expback() {
		return oe_expback;
	}

	public void setOe_expback(Date oe_expback) {
		this.oe_expback = oe_expback;
	}

	public Timestamp getOe_get() {
		return oe_get;
	}

	public void setOe_get(Timestamp oe_get) {
		this.oe_get = oe_get;
	}

	public Timestamp getOe_back() {
		return oe_back;
	}

	public void setOe_back(Timestamp oe_back) {
		this.oe_back = oe_back;
	}

	public Integer getOe_reqty() {
		return oe_reqty;
	}

	public void setOe_reqty(Integer oe_reqty) {
		this.oe_reqty = oe_reqty;
	}

	public Timestamp getOe_change() {
		return oe_change;
	}

	public void setOe_change(Timestamp oe_change) {
		this.oe_change = oe_change;
	}

	public Timestamp getOe_estbl() {
		return oe_estbl;
	}

	public void setOe_estbl(Timestamp oe_estbl) {
		this.oe_estbl = oe_estbl;
	}
}
