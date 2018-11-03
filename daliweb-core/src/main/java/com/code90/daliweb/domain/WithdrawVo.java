package com.code90.daliweb.domain;

/**
 * 提现展示类
 * @author Ray Lin
 * @create 2018-10-28 15:04
 **/
public class WithdrawVo extends BaseDomain {
    private static final long serialVersionUID = -4231968484012766612L;
    private String id;
    private double money;
    private double feeMoney;
    private double taxMoney;
    private double payMoney;
    private int status;
    private String description;
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(double feeMoney) {
        this.feeMoney = feeMoney;
    }

    public double getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(double taxMoney) {
        this.taxMoney = taxMoney;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
