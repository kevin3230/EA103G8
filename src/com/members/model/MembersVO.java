package com.members.model;

import java.io.Serializable;
import java.sql.Date;

public class MembersVO implements Serializable{

	private String mem_no;
	private String mem_email;
	private String mem_pwd;
	private String mem_name;
	private String mem_alias;
	private String mem_gender;
	private Date mem_birth;
	private String mem_mobile;
	private String mem_tel;
	private String mem_addr;
	private Integer mem_type;
	private Date mem_regdate;
	private Integer mem_stat;
	

	public String getMem_no() {
		return mem_no;
	}

	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}

	public String getMem_email() {
		return mem_email;
	}

	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}

	public String getMem_pwd() {
		return mem_pwd;
	}

	public void setMem_pwd(String mem_pwd) {
		this.mem_pwd = mem_pwd;
	}

	public String getMem_name() {
		return mem_name;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

	public String getMem_alias() {
		return mem_alias;
	}

	public void setMem_alias(String mem_alias) {
		this.mem_alias = mem_alias;
	}

	public String getMem_gender() {
		return mem_gender;
	}

	public void setMem_gender(String mem_gender) {
		this.mem_gender = mem_gender;
	}

	public Date getMem_birth() {
		return mem_birth;
	}

	public void setMem_birth(Date mem_birth) {
		this.mem_birth = mem_birth;
	}

	public String getMem_mobile() {
		return mem_mobile;
	}

	public void setMem_mobile(String mem_mobile) {
		this.mem_mobile = mem_mobile;
	}

	public String getMem_tel() {
		return mem_tel;
	}

	public void setMem_tel(String mem_tel) {
		this.mem_tel = mem_tel;
	}

	public String getMem_addr() {
		return mem_addr;
	}

	public void setMem_addr(String mem_addr) {
		this.mem_addr = mem_addr;
	}

	public Integer getMem_type() {
		return mem_type;
	}

	public void setMem_type(int mem_type) {
		this.mem_type = mem_type;
	}

	public Date getMem_regdate() {
		return mem_regdate;
	}

	public void setMem_regdate(Date mem_regdate) {
		this.mem_regdate = mem_regdate;
	}

	public Integer getMem_stat() {
		return mem_stat;
	}

	public void setMem_stat(int mem_stat) {
		this.mem_stat = mem_stat;
	}
	
}