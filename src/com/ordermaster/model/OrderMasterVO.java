package com.ordermaster.model;

import java.sql.Date;
import java.sql.Timestamp;

public class OrderMasterVO implements java.io.Serializable {

	private static final long serialVersionUID = 2838535658153137833L;

	private String om_no;
	private String om_memno;
	private String om_vdno;
	private Integer om_stat;
	private String om_note;
	private Timestamp om_estbl;
	private Integer om_txnamt;
	private Date om_txntime;
	private String om_cardno;

	public OrderMasterVO() {
	}

	public OrderMasterVO(String om_memno, String om_vdno, int om_txnamt, String om_cardno) {
		this.setOm_memno(om_memno);
		this.setOm_vdno(om_vdno);
		this.setOm_txnamt(om_txnamt);
		this.setOm_cardno(om_cardno);
	}

	public String getOm_no() {
		return om_no;
	}

	public void setOm_no(String om_no) {
		this.om_no = om_no;
	}

	public String getOm_memno() {
		return om_memno;
	}

	public void setOm_memno(String om_memno) {
		this.om_memno = om_memno;
	}

	public String getOm_vdno() {
		return om_vdno;
	}

	public void setOm_vdno(String om_vdno) {
		this.om_vdno = om_vdno;
	}

	public Integer getOm_stat() {
		return om_stat;
	}

	public void setOm_stat(Integer om_stat) {
		this.om_stat = om_stat;
	}

	public String getOm_note() {
		return om_note;
	}

	public void setOm_note(String om_note) {
		this.om_note = om_note;
	}

	public Timestamp getOm_estbl() {
		return om_estbl;
	}

	public void setOm_estbl(Timestamp om_estbl) {
		this.om_estbl = om_estbl;
	}

	public Integer getOm_txnamt() {
		return om_txnamt;
	}

	public void setOm_txnamt(Integer om_txnamt) {
		this.om_txnamt = om_txnamt;
	}

	public Date getOm_txntime() {
		return om_txntime;
	}

	public void setOm_txntime(Date om_txntime) {
		this.om_txntime = om_txntime;
	}

	public String getOm_cardno() {
		return om_cardno;
	}

	public void setOm_cardno(String om_cardno) {
		this.om_cardno = om_cardno;
	}

}
