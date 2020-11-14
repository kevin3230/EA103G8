package com.ordercamp.model;

import java.sql.Date;

public class OrderCampVO implements java.io.Serializable{
	private static final long serialVersionUID = -2641786493179451745L;
	
	private String oc_no;
	private String oc_campno;
	private String oc_omno;
	private Integer oc_qty;
	private Integer oc_listprice;
	private Integer oc_price;
	private Integer oc_stat;
	private Date oc_start;
	private Date oc_end;
	private Date oc_change;
	private Date oc_estbl;

	public OrderCampVO() {
	}

	// Author Jeff
	public OrderCampVO(String oc_campno, int oc_qty, int oc_listprice, int oc_price, Date oc_start, Date oc_end) {
		/*
		 * This constructor used for OrderMasterJDBCDAO testing for OrderMaster
		 * insertWithDetails method.
		 */
		this.setOc_campno(oc_campno);
		this.setOc_qty(oc_qty);
		this.setOc_listprice(oc_listprice);
		this.setOc_price(oc_price);
		this.setOc_start(oc_start);
		this.setOc_end(oc_end);
	}
	
	// Author Jeff
	public OrderCampVO(String om_no, String oc_campno, int oc_qty, int oc_listprice, int oc_price, Date oc_start, Date oc_end) {
		/*
		 * This constructor used for OrderMasterJDBCDAO testing for OrderMaster
		 * updateWithDetails method.
		 */
		this.setOc_omno(om_no);
		this.setOc_campno(oc_campno);
		this.setOc_qty(oc_qty);
		this.setOc_listprice(oc_listprice);
		this.setOc_price(oc_price);
		this.setOc_start(oc_start);
		this.setOc_end(oc_end);
	}

	public String getOc_no() {
		return oc_no;
	}

	public void setOc_no(String oc_no) {
		this.oc_no = oc_no;
	}

	public String getOc_campno() {
		return oc_campno;
	}

	public void setOc_campno(String oc_campno) {
		this.oc_campno = oc_campno;
	}

	public String getOc_omno() {
		return oc_omno;
	}

	public void setOc_omno(String oc_omno) {
		this.oc_omno = oc_omno;
	}

	public Integer getOc_qty() {
		return oc_qty;
	}

	public void setOc_qty(Integer oc_qty) {
		this.oc_qty = oc_qty;
	}

	public Integer getOc_listprice() {
		return oc_listprice;
	}

	public void setOc_listprice(Integer oc_listprice) {
		this.oc_listprice = oc_listprice;
	}

	public Integer getOc_price() {
		return oc_price;
	}

	public void setOc_price(Integer oc_price) {
		this.oc_price = oc_price;
	}

	public Integer getOc_stat() {
		return oc_stat;
	}

	public void setOc_stat(Integer oc_stat) {
		this.oc_stat = oc_stat;
	}

	public Date getOc_start() {
		return oc_start;
	}

	public void setOc_start(Date oc_start) {
		this.oc_start = oc_start;
	}

	public Date getOc_end() {
		return oc_end;
	}

	public void setOc_end(Date oc_end) {
		this.oc_end = oc_end;
	}

	public Date getOc_change() {
		return oc_change;
	}

	public void setOc_change(Date oc_change) {
		this.oc_change = oc_change;
	}

	public Date getOc_estbl() {
		return oc_estbl;
	}

	public void setOc_estbl(Date oc_estbl) {
		this.oc_estbl = oc_estbl;
	}
}