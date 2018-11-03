package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单修改请求类
 * @author Ray Lin
 * @create 2018-09-15 15:10
 **/
public class OrdersChangeReq extends CommonRequest {
    private String id;
    private String receiver;
    private String phone;
    private String area;
    private String address;
    private String postalCode;
    private String description;
    private double deductionMonry;
    private double feeMoney;
    private int status;
    private String shipNo;
    private List<OrderDetailSaveReq> orderDetailSaveReqs=new ArrayList<>();

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OrderDetailSaveReq> getOrderDetailSaveReqs() {
        return orderDetailSaveReqs;
    }

    public void setOrderDetailSaveReqs(List<OrderDetailSaveReq> orderDetailSaveReqs) {
        this.orderDetailSaveReqs = orderDetailSaveReqs;
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

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }
}
