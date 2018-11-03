package com.code90.daliweb.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单展示实现类
 * @author Ray Lin
 * @create 2018-09-15 15:35
 **/
public class OrdersVo extends BaseDomain {
    private String id;
    private String receiver;
    private String phone;
    private String area;
    private String address;
    private String postalCode;
    private String description;
    private double totalMoney;
    private double deductionMonry;
    private double feeMoney;
    private String payNo;
    private Date payTime;
    private int payType;
    private int status;
    private int isRefund;
    private String shipNo;
    private List<CommodityVo> orderCommodities=new ArrayList<>();

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

    public List<CommodityVo> getOrderCommodities() {
        return orderCommodities;
    }

    public void setOrderCommodities(List<CommodityVo> orderCommodities) {
        this.orderCommodities = orderCommodities;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(int isRefund) {
        this.isRefund = isRefund;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }
}
