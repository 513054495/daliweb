package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;


/**
 * 代理新增请求类
 * @author Ray Lin
 * @create 2018-10-23 23:52
 **/
public class ProxySaveReq extends CommonRequest {
    private String bankName;
    private String bankCode;
    private int type;
    private String area;
    private String postalCode;
    private String createBy;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
