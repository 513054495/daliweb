package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 代理详情实体类
 * @author Ray Lin
 * @create 2018-10-22 21:57
 **/
@Entity
@Table(name = "sys_proxy_detail")
public class ProxyDetail extends BaseDomain {
    private static final long serialVersionUID = -8972727056303527165L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //购买产品帐号
    @Column
    private String buyUserCode;
    //订单编号
    @Column
    private String orderNo;
    //获得代理金额
    @Column
    private double money;
    //代理类型
    @Column
    private int type;
    //代理获得状态
    @Column
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyUserCode() {
        return buyUserCode;
    }

    public void setBuyUserCode(String buyUserCode) {
        this.buyUserCode = buyUserCode;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
