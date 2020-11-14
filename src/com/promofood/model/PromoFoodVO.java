package com.promofood.model;

public class PromoFoodVO {
	private String pf_prono;
	private String pf_foodno;
	private int pf_price;

	public PromoFoodVO() {
	}

	public PromoFoodVO(String pf_prono, String pf_foodno, int pf_price) {
		this.setPf_prono(pf_prono);
		this.setPf_foodno(pf_foodno);
		this.setPf_price(pf_price);
	}

	public String getPf_prono() {
		return pf_prono;
	}

	public void setPf_prono(String pf_prono) {
		this.pf_prono = pf_prono;
	}

	public String getPf_foodno() {
		return pf_foodno;
	}

	public void setPf_foodno(String pf_foodno) {
		this.pf_foodno = pf_foodno;
	}

	public int getPf_price() {
		return pf_price;
	}

	public void setPf_price(int pf_price) {
		this.pf_price = pf_price;
	}

}
