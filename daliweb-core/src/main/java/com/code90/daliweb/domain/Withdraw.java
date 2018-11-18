package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 提现实体类
 * @author Ray Lin
 * @create 2018-10-28 15:04
 **/
@Entity
@Table(name = "sys_withdraw")
public class Withdraw extends BaseDomain {
    private static final long serialVersionUID = -6301170742400714951L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //提现金额
    @Column
    private double money;
    //手续费金额
    @Column
    private double feeMoney;
    //扣税金额
    @Column
    private double taxMoney;
    //实际支付
    @Column
    private double payMoney;
    //状态(0.待审核 1.已通过 2.已拒绝)
    @Column
    private int status;
    //审核描述
    @Column
    @Lob
    private String description;

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
}
