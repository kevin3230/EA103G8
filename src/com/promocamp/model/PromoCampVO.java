package com.promocamp.model;

public class PromoCampVO {
    private String pc_prono;
    private String pc_campno;
    private int pc_price;

    public PromoCampVO() {
    }

    public PromoCampVO(String pc_prono, String pc_campno, int pc_price) {
        this.setPc_prono(pc_prono);
        this.setPc_campno(pc_campno);
        this.setPc_price(pc_price);
    }

    public String getPc_prono() {
        return pc_prono;
    }

    public void setPc_prono(String pc_prono) {
        this.pc_prono = pc_prono;
    }

    public String getPc_campno() {
        return pc_campno;
    }

    public void setPc_campno(String pc_campno) {
        this.pc_campno = pc_campno;
    }

    public int getPc_price() {
        return pc_price;
    }

    public void setPc_price(int pc_price) {
        this.pc_price = pc_price;
    }

}
