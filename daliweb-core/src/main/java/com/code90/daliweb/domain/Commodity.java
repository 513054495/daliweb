package com.code90.daliweb.domain;

import javax.persistence.*;

/**
 * 商品实体类
 * @author Ray Lin
 * @create 2018-09-11 22:02
 **/
@Entity
@Table(name = "sys_commodity")
public class Commodity extends BaseDomain {
    @Id
    @Column(name="ID",length=36)
    //商品编号
    private String id;
    //商品名称
    @Column
    private String name;
    //商品类型（0.普通商品，1.虚拟商品）
    @Column
    private int type;
    //商品总数
    @Column
    private int totalNum;
    //商品单价
    @Column
    private double price;
    //商品图片
    @Column
    @Lob
    private String pic;
    //商品详情图片
    @Column
    @Lob
    private String descPic;
    //商品描述
    @Column
    @Lob
    private String description;
    //是否购买升VIP用户（0.否，1.是）
    @Column
    private int isVip;
    //红包抵扣比例
    @Column
    private double deduction;
    //商品状态(0.下架，1.上架)
    @Column
    private int status;
    //是否删除
    @Column
    private int deleted;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescPic() {
        return descPic;
    }

    public void setDescPic(String descPic) {
        this.descPic = descPic;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

}
