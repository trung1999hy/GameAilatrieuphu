package com.example.myapplication;

public class listcauhoi {
    private int id;
    private String cauhoi;
    private String cauA;
    private String cauB;
    private String cauC;
    private String cauD;
    private int dapandung;
    private int lv;

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCauhoi() {
        return cauhoi;
    }

    public void setCauhoi(String cauhoi) {
        this.cauhoi = cauhoi;
    }

    public String getCauA() {
        return cauA;
    }

    public void setCauA(String cauA) {
        this.cauA = cauA;
    }

    public String getCauB() {
        return cauB;
    }

    public void setCauB(String cauB) {
        this.cauB = cauB;
    }

    public String getCauC() {
        return cauC;
    }

    public void setCauC(String cauC) {
        this.cauC = cauC;
    }

    public String getCauD() {
        return cauD;
    }

    public void setCauD(String cauD) {
        this.cauD = cauD;
    }

    public int getDapandung() {
        return dapandung;
    }

    public void setDapandung(int dapandung) {
        this.dapandung = dapandung;
    }

    public listcauhoi(int id, String cauhoi, String cauA, String cauB, String cauC, String cauD, int dapandung) {
        this.id = id;
        this.cauhoi = cauhoi;
        this.cauA = cauA;
        this.cauB = cauB;
        this.cauC = cauC;
        this.cauD = cauD;
        this.dapandung = dapandung;
        this.lv = lv;
    }

}