package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 商品规格实体类
 * @author Ray Lin
 * @create 2019-01-18 0:02
 **/
@Entity
@Table(name="sys_commodity_norm")
public class CommodityNorm extends BaseDomain{
    private static final long serialVersionUID = -1579497167964287387L;
    @Id
    private String id;
    //规格名称
    @Column
    private String norm;
    //库存数
    @Column
    private int totalNum;
    //单价
    @Column
    private double price;
    //红包抵扣
    @Column
    private double deduction;
    //所属商品
    @Column
    private String commodityId;
    //是否删除
    @Column
    private int isDelete;
    //结算价
    @Column
    private double settlement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
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

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public double getSettlement() {
        return settlement;
    }

    public void setSettlement(double settlement) {
        this.settlement = settlement;
    }
}
