package com.foodtype.model;

public class FoodTypeVO implements java.io.Serializable {
	String ft_no;
	String ft_name;
	
	public FoodTypeVO() {
		super();
	}
	public FoodTypeVO(String ft_no, String ft_name) {
		super();
		this.ft_no = ft_no;
		this.ft_name = ft_name;
	}
	public String getFt_no() {
		return ft_no;
	}
	public void setFt_no(String ft_no) {
		this.ft_no = ft_no;
	}
	public String getFt_name() {
		return ft_name;
	}
	public void setFt_name(String ft_name) {
		this.ft_name = ft_name;
	}

}
