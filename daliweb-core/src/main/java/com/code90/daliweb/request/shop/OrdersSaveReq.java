package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单生成请求类
 * @author Ray Lin
 * @create 2018-09-15 1:33
 **/
public class OrdersSaveReq extends CommonRequest {
    private String receiver;
    private String phone;
    private String area;
    private String address;
    private String postalCode;
    private String description;
    private double totalMoney;
    private double deductionMonry;
    private double feeMoney;
    private int status;
    private String createBy;
    private int isShoppingCart;
    private String aSqzwx;
    private String orderPostalCode;
    private List<OrderDetailSaveReq> orderDetailSaveReqs=new ArrayList<>();

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public int getIsShoppingCart() {
        return isShoppingCart;
    }

    public void setIsShoppingCart(int isShoppingCart) {
        this.isShoppingCart = isShoppingCart;
    }

    public String getaSqzwx() {
        return aSqzwx;
    }

    public void setaSqzwx(String aSqzwx) {
        this.aSqzwx = aSqzwx;
    }
}
