package com.promoeqpt.model;

public class PromoEqptVO {
	private String pe_prono;
	private String pe_eqptno;
	private int pe_price;

	public PromoEqptVO() {
	}

	public PromoEqptVO(String pe_prono, String pe_eqptno, int pe_price) {
		this.setPe_prono(pe_prono);
		this.setPe_eqptno(pe_eqptno);
		this.setPe_price(pe_price);
	}

	public String getPe_prono() {
		return pe_prono;
	}

	public void setPe_prono(String pe_prono) {
		this.pe_prono = pe_prono;
	}

	public String getPe_eqptno() {
		return pe_eqptno;
	}

	public void setPe_eqptno(String pe_eqptno) {
		this.pe_eqptno = pe_eqptno;
	}

	public int getPe_price() {
		return pe_price;
	}

	public void setPe_price(int pe_price) {
		this.pe_price = pe_price;
	}

}
