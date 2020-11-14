package com.vendor.model;
import java.sql.Date;
import java.sql.Timestamp;

public class VendorLeaseEqptVO implements java.io.Serializable{
	private String om_no; //主訂單
	private String om_memno;
	private String om_vdno;
	private String mem_name;//會員
	private Integer om_stat;
	private Date oc_start;
	private Date oc_end;
	private String eqpt_name;//裝備
	private String oe_no;
	private String oe_eqptno;
	private Integer oe_qty;
	private Integer oe_stat;
	private Date oe_expget;
	private Date oe_expback;
	private Timestamp oe_get;
	private Timestamp oe_back;
	private Integer oe_reqty;

	public String getOm_no() {
		return om_no;
	}
	public void setOm_no(String om_no) {
		this.om_no = om_no;
	}
	public Integer getOm_stat() {
		return om_stat;
	}
	public void setOm_stat(Integer om_stat) {
		this.om_stat = om_stat;
	}
	public String getOm_memno() {
		return om_memno;
	}
	public void setOm_memno(String om_memno) {
		this.om_memno = om_memno;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
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
	public String getOm_vdno() {
		return om_vdno;
	}
	public void setOm_vdno(String om_vdno) {
		this.om_vdno = om_vdno;
	}
	public String getEqpt_name() {
		return eqpt_name;
	}
	public void setEqpt_name(String eqpt_name) {
		this.eqpt_name = eqpt_name;
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
	public Integer getOe_qty() {
		return oe_qty;
	}
	public void setOe_qty(Integer oe_qty) {
		this.oe_qty = oe_qty;
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

	
	
	
}
