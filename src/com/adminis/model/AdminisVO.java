package com.adminis.model;

import java.io.Serializable;
import java.sql.Date;

//此類別對應到資料庫表格
public class AdminisVO implements Serializable {

	// UID可以不加入，因為資料庫表格在建立時應該就定義了相關的規定，不會隨意的變動。
	private static final long serialVersionUID = -497780802638168635L;

	// 實體變數對應到部們表格的各個欄位
	private String adminis_no;
	private String adminis_name;
	private String adminis_pwd;
	private String adminis_email;
	private String adminis_dept;
	private Date adminis_regdate;
	private Integer adminis_pv;
	private Integer adminis_stat;

	
	
	
	public Date getAdminis_regdate() {
		return adminis_regdate;
	}

	public void setAdminis_regdate(Date adminis_regdate) {
		this.adminis_regdate = adminis_regdate;
	}

	public Integer getAdminis_stat() {
		return adminis_stat;
	}

	public void setAdminis_stat(Integer adminis_stat) {
		this.adminis_stat = adminis_stat;
	}

	public String getAdminis_no() {
		return adminis_no;
	}

	public void setAdminis_no(String adminis_no) {
		this.adminis_no = adminis_no;
	}

	public String getAdminis_name() {
		return adminis_name;
	}

	public void setAdminis_name(String adminis_name) {
		this.adminis_name = adminis_name;
	}

	public String getAdminis_pwd() {
		return adminis_pwd;
	}

	public void setAdminis_pwd(String adminis_pwd) {
		this.adminis_pwd = adminis_pwd;
	}

	public String getAdminis_email() {
		return adminis_email;
	}

	public void setAdminis_email(String adminis_email) {
		this.adminis_email = adminis_email;
	}

	public String getAdminis_dept() {
		return adminis_dept;
	}

	public void setAdminis_dept(String adminis_dept) {
		this.adminis_dept = adminis_dept;
	}

	public Integer getAdminis_pv() {
		return adminis_pv;
	}

	public void setAdminis_pv(Integer adminis_pv) {
		this.adminis_pv = adminis_pv;
	}

}
