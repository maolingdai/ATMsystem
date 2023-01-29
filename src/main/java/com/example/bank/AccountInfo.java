package com.example.bank;

/**
 * 账户类
 */
public class AccountInfo {
    private String accountId;
    private String name;
    private String pwd;
    private double balance;
    private double limit;


    public AccountInfo(String accountId, String name, String pwd, double balance, double limit) {
        this.accountId = accountId;
        this.name = name;
        this.pwd = pwd;
        this.balance = balance;
        this.limit = limit;
    }

    public AccountInfo(String accountId, String name, String pwd, double limit) {
        this.accountId = accountId;
        this.name = name;
        this.pwd = pwd;
        this.limit = limit;
    }

    public AccountInfo() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
