package com.activity.model;

import java.io.Serializable;
import java.sql.Date;

//此類別對應到資料庫表格
public class ActivityVO implements Serializable {

	// UID可以不加入，因為資料庫表格在建立時應該就定義了相關的規定，不會隨意的變動。
	private static final long serialVersionUID = -5901350801140957708L;

	// 實體變數對應到部們表格的各個欄位
	private String act_no;
	private String act_title;
	private String act_content;
	private Date act_edit;
	private Integer act_stat;
	private String act_adminisno;

	public Date getAct_edit() {
		return act_edit;
	}

	public void setAct_edit(Date act_edit) {
		this.act_edit = act_edit;
	}

	public Integer getAct_stat() {
		return act_stat;
	}

	public void setAct_stat(Integer act_stat) {
		this.act_stat = act_stat;
	}

	public String getAct_no() {
		return act_no;
	}

	public void setAct_no(String act_no) {
		this.act_no = act_no;
	}

	public String getAct_title() {
		return act_title;
	}

	public void setAct_title(String act_title) {
		this.act_title = act_title;
	}

	public String getAct_content() {
		return act_content;
	}

	public void setAct_content(String act_content) {
		this.act_content = act_content;
	}

	public String getAct_adminisno() {
		return act_adminisno;
	}

	public void setAct_adminisno(String act_adminisno) {
		this.act_adminisno = act_adminisno;
	}

}