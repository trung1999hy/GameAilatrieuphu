package com.example.myapplication;

public class User {
    private String accountNumber;
    private String bank;
    private String accountName;
    private String numberPhone;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }



    public User(String accountNumber, String bank, String accountName, String numberPhone) {
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.accountName = accountName;
        this.numberPhone = numberPhone;
    }

}