package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 代理规则实体类
 * @author Ray Lin
 * @create 2018-10-22 22:08
 **/
@Entity
@Table(name = "sys_rules")
public class Rules extends BaseDomain {
    private static final long serialVersionUID = -5539939861177947318L;
    @Id
    private int id;
    //规则类型(0.定价，1.成交价)
    @Column
    private int type;
    //国家代理基数
    @Column
    private int countryNum;
    //省级代理基数
    @Column
    private int provinceNum;
    //市级代理基数
    @Column
    private int cityNum;
    //县级代理基数
    @Column
    private int countyNum;
    //国家代理推广基数
    @Column
    private int countryPromotionNum;
    //省级代理推广基数
    @Column
    private int provincePromotionNum;
    //市级代理推广基数
    @Column
    private int cityPromotionNum;
    //县级代理推广基数
    @Column
    private int countyPromotionNum;
    //学校代理基数
    @Column
    private int schoolNum;
    //华文代理基数
    @Column
    private int huawenNum;
    //古韵代理基数
    @Column
    private int guyunNum;
    //训诂代理基数
    @Column
    private int xunguNum;
    //提现门槛
    @Column
    private int withdrawNum;
    //手续费
    @Column
    private int fee;
    //税收
    @Column
    private int tax;
    //1-5元红包出现几率
    @Column
    private int level1;
    //6-10元红包出现几率
    @Column
    private int level2;
    //11-20元红包出现几率
    @Column
    private int level3;
    //20-50元红包出现几率
    @Column
    private int level4;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
    }

    public int getLevel4() {
        return level4;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
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

    public int getCityPromotionNum() {
        return cityPromotionNum;
    }

    public void setCityPromotionNum(int cityPromotionNum) {
        this.cityPromotionNum = cityPromotionNum;
    }

    public int getCountyPromotionNum() {
        return countyPromotionNum;
    }

    public void setCountyPromotionNum(int countyPromotionNum) {
        this.countyPromotionNum = countyPromotionNum;
    }
}
