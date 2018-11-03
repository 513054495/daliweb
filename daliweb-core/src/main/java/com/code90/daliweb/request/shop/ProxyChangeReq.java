package com.code90.daliweb.request.shop;

import com.code90.daliweb.request.CommonRequest;

/**
 * 提现代理规则修改请求类
 * @author Ray Lin
 * @create 2018-10-22 23:08
 **/
public class ProxyChangeReq extends CommonRequest {
    private int type;
    private int countryNum;
    private int provinceNum;
    private int cityNum;
    private int countyNum;
    private int countryPromotionNum;
    private int provincePromotionNum;
    private int cityPromotionNum;
    private int countyPromotionNum;
    private int schoolNum;
    private int huawenNum;
    private int guyunNum;
    private int xunguNum;
    private int withdrawNum;
    private int fee;
    private int tax;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCountryNum() {
        return countryNum;
    }

    public void setCountryNum(int countryNum) {
        this.countryNum = countryNum;
    }

    public int getProvinceNum() {
        return provinceNum;
    }

    public void setProvinceNum(int provinceNum) {
        this.provinceNum = provinceNum;
    }

    public int getCityNum() {
        return cityNum;
    }

    public void setCityNum(int cityNum) {
        this.cityNum = cityNum;
    }

    public int getCountyNum() {
        return countyNum;
    }

    public void setCountyNum(int countyNum) {
        this.countyNum = countyNum;
    }

    public int getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(int schoolNum) {
        this.schoolNum = schoolNum;
    }

    public int getHuawenNum() {
        return huawenNum;
    }

    public void setHuawenNum(int huawenNum) {
        this.huawenNum = huawenNum;
    }

    public int getGuyunNum() {
        return guyunNum;
    }

    public void setGuyunNum(int guyunNum) {
        this.guyunNum = guyunNum;
    }

    public int getXunguNum() {
        return xunguNum;
    }

    public void setXunguNum(int xunguNum) {
        this.xunguNum = xunguNum;
    }

    public int getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(int withdrawNum) {
        this.withdrawNum = withdrawNum;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getCountryPromotionNum() {
        return countryPromotionNum;
    }

    public void setCountryPromotionNum(int countryPromotionNum) {
        this.countryPromotionNum = countryPromotionNum;
    }

    public int getProvincePromotionNum() {
        return provincePromotionNum;
    }

    public void setProvincePromotionNum(int provincePromotionNum) {
        this.provincePromotionNum = provincePromotionNum;
    }

    public int getCountyPromotionNum() {
        return countyPromotionNum;
    }

    public void setCountyPromotionNum(int countyPromotionNum) {
        this.countyPromotionNum = countyPromotionNum;
    }

    public int getCityPromotionNum() {
        return cityPromotionNum;
    }

    public void setCityPromotionNum(int cityPromotionNum) {
        this.cityPromotionNum = cityPromotionNum;
    }
}
