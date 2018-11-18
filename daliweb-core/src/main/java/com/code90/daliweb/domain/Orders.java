package com.code90.daliweb.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单实体类
 * @author Ray Lin
 * @create 2018-09-15 0:51
 **/
@Entity
@Table(name="sys_order")
public class Orders extends  BaseDomain {
    private static final long serialVersionUID = 5984691558057991835L;
    @Id
    @Column(name="ID",length=36)
    //订单编号
    private String id;
    //收货人
    @Column
    private String receiver;
    //收货人电话
    @Column
    private String phone;
    //收货人地区
    @Column
    private String area;
    //收货人详细地址
    @Column
    private String address;
    //邮编
    @Column
    private String postalCode;
    //订单备注
    @Column
    private String description;
    //订单总金额
    @Column
    private double totalMoney;
    //红包抵扣金额
    @Column
    private double deductionMonry;
    //支付手续费金额
    @Column
    private double feeMoney;
    //支付流水号
    @Column
    private String payNo;
    //支付日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="pay_time")
    private Date payTime;
    //支付方式(1.微信支付 2.支付宝支付）
    @Column
    private int payType;
    //订单状态(0-待付款，1-待发货，2-待收货，3-交易完成，4-交易关闭)
    @Column
    private int status;
    @Column
    private String shipNo;
    //下单邮编
    @Column
    private String orderPostalCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getDeductionMonry() {
        return deductionMonry;
    }

    public void setDeductionMonry(double deductionMonry) {
        this.deductionMonry = deductionMonry;
    }

    public double getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(double feeMoney) {
        this.feeMoney = feeMoney;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getOrderPostalCode() {
        return orderPostalCode;
    }

    public void setOrderPostalCode(String orderPostalCode) {
        this.orderPostalCode = orderPostalCode;
    }
}
