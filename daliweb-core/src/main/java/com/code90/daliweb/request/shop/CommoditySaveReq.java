package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

import javax.persistence.Column;

/**
 * 商品新增请求类
 * @author Ray Lin
 * @create 2018-09-12 22:48
 **/
public class CommoditySaveReq extends CommonRequest {
    private String name;
    private int type;
    private int totalNum;
    private double price;
    private String pic;
    private String descPic;
    private String description;
    private int isVip;
    private double deduction;
    private int status;
    private String specificationList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescPic() {
        return descPic;
    }

    public void setDescPic(String descPic) {
        this.descPic = descPic;
    }

    public String getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(String specificationList) {
        this.specificationList = specificationList;
    }
}
