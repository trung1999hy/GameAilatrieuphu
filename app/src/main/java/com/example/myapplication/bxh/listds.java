package com.example.myapplication.bxh;

public class listds {
    public String ten;

   public int vcoin;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getCoin() {
        return vcoin;
    }

    public void setCoin(int coin) {
        this.vcoin = coin;
    }

    public listds(String ten, int coin) {
        this.ten = ten;
        this.vcoin = coin;
    }

    public listds() {

    }
    public String toString() {
        return "Tên : "+ten+"\nVàng: "+vcoin+"";
    }
}
