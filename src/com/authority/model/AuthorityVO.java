package com.authority.model;

import java.io.Serializable;

//此類別對應到資料庫表格
public class AuthorityVO implements Serializable {

	// UID可以不加入，因為資料庫表格在建立時應該就定義了相關的規定，不會隨意的變動。
	private static final long serialVersionUID = 2621248327773783947L;

	// 實體變數對應到部們表格的各個欄位

	private Integer auth_no;
	private String auth_name;

	public Integer getAuth_no() {
		return auth_no;
	}

	public void setAuth_no(Integer auth_no) {
		this.auth_no = auth_no;
	}

	public String getAuth_name() {
		return auth_name;
	}

	public void setAuth_name(String auth_name) {
		this.auth_name = auth_name;
	}

}
