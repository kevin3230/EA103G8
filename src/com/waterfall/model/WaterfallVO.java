package com.waterfall.model;
import java.sql.Date;

public class WaterfallVO implements java.io.Serializable {

	private String wf_no;
	private String wf_memno;
	private String wf_title;
	private String wf_content;
	private String wf_edit;
	private Integer wf_stat;
	
	public String getWf_no() {
		return wf_no;
	}
	public void setWf_no(String wf_no) {
		this.wf_no = wf_no;
	}
	public String getWf_memno() {
		return wf_memno;
	}
	public void setWf_memno(String wf_memno) {
		this.wf_memno = wf_memno;
	}
	public String getWf_title() {
		return wf_title;
	}
	public void setWf_title(String wf_title) {
		this.wf_title = wf_title;
	}
	public String getWf_content() {
		return wf_content;
	}
	public void setWf_content(String wf_content) {
		this.wf_content = wf_content;
	}
	public String getWf_edit() {
		return wf_edit;
	}
	public void setWf_edit(String wf_edit) {
		this.wf_edit = wf_edit;
	}
	public int getWf_stat() {
		return wf_stat;
	}
	public void setWf_stat(int wf_stat) {
		this.wf_stat = wf_stat;
	}
	
	
}
