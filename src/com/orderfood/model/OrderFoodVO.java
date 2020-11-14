package com.orderfood.model;

import java.sql.Timestamp;

public class OrderFoodVO implements java.io.Serializable {
	private static final long serialVersionUID = 7054566835401819953L;
	
	private String of_no;
	private String of_foodno;
	private String of_omno;
	private Integer of_qty;
	private Integer of_listprice;
	private Integer of_price;
	private Integer of_stat;
	private Timestamp of_change;
	private Timestamp of_estbl;

	public OrderFoodVO() {
	}

	// Author Jeff
	public OrderFoodVO(String of_foodno, int of_qty, int of_listprice, int of_price) {
		/*
		 * This constructor used for OrderMasterJDBCDAO testing for OrderMaster
		 * insertWithDetails method.
		 */
		this.setOf_foodno(of_foodno);
		this.setOf_qty(of_qty);
		this.setOf_listprice(of_listprice);
		this.setOf_price(of_price);
	}
	
	// Author Jeff
	public OrderFoodVO(String om_no, String of_foodno, int of_qty, int of_listprice, int of_price) {
		/*
		 * This constructor used for OrderMasterJDBCDAO testing for OrderMaster
		 * updateWithDetails method.
		 */
		this.setOf_omno(om_no);
		this.setOf_foodno(of_foodno);
		this.setOf_qty(of_qty);
		this.setOf_listprice(of_listprice);
		this.setOf_price(of_price);
	}

	public String getOf_no() {
		return of_no;
	}

	public void setOf_no(String of_no) {
		this.of_no = of_no;
	}

	public String getOf_foodno() {
		return of_foodno;
	}

	public void setOf_foodno(String of_foodno) {
		this.of_foodno = of_foodno;
	}

	public String getOf_omno() {
		return of_omno;
	}

	public void setOf_omno(String of_omno) {
		this.of_omno = of_omno;
	}

	public Integer getOf_qty() {
		return of_qty;
	}

	public void setOf_qty(Integer of_qty) {
		this.of_qty = of_qty;
	}

	public Integer getOf_listprice() {
		return of_listprice;
	}

	public void setOf_listprice(Integer of_listprice) {
		this.of_listprice = of_listprice;
	}

	public Integer getOf_price() {
		return of_price;
	}

	public void setOf_price(Integer of_price) {
		this.of_price = of_price;
	}

	public Integer getOf_stat() {
		return of_stat;
	}

	public void setOf_stat(Integer of_stat) {
		this.of_stat = of_stat;
	}

	public Timestamp getOf_change() {
		return of_change;
	}

	public void setOf_change(Timestamp of_change) {
		this.of_change = of_change;
	}

	public Timestamp getOf_estbl() {
		return of_estbl;
	}

	public void setOf_estbl(Timestamp of_estbl) {
		this.of_estbl = of_estbl;
	}

}
