package com.food.model;

public class FoodVO implements java.io.Serializable {
	private String food_no;
	private String food_vdno;
	private String food_name;
	private String food_ftno;
	private Integer food_price;
	private String food_intro;
	private Integer food_stat;
	private byte[] food_pic;
	
	public FoodVO() {
		super();
	}

	public FoodVO(String food_no, String food_vdno, String food_name, String food_ftno, Integer food_price,
			String food_intro, Integer food_stat, byte[] food_pic) {
		super();
		this.food_no = food_no;
		this.food_vdno = food_vdno;
		this.food_name = food_name;
		this.food_ftno = food_ftno;
		this.food_price = food_price;
		this.food_intro = food_intro;
		this.food_stat = food_stat;
		this.food_pic = food_pic;
	}

	public String getFood_no() {
		return food_no;
	}

	public void setFood_no(String food_no) {
		this.food_no = food_no;
	}

	public String getFood_vdno() {
		return food_vdno;
	}

	public void setFood_vdno(String food_vdno) {
		this.food_vdno = food_vdno;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public String getFood_ftno() {
		return food_ftno;
	}

	public void setFood_ftno(String food_ftno) {
		this.food_ftno = food_ftno;
	}

	public Integer getFood_price() {
		return food_price;
	}

	public void setFood_price(Integer food_price) {
		this.food_price = food_price;
	}

	public String getFood_intro() {
		return food_intro;
	}

	public void setFood_intro(String food_intro) {
		this.food_intro = food_intro;
	}

	public Integer getFood_stat() {
		return food_stat;
	}

	public void setFood_stat(Integer food_stat) {
		this.food_stat = food_stat;
	}

	public byte[] getFood_pic() {
		return food_pic;
	}

	public void setFood_pic(byte[] food_pic) {
		this.food_pic = food_pic;
	}

}
