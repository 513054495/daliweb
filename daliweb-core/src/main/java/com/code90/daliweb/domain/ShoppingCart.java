package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 购物车实体类
 * @author Ray Lin
 * @create 2018-09-15 23:17
 **/
@Entity
@Table(name="sys_shopping_cart")
public class ShoppingCart extends BaseDomain {
    private static final long serialVersionUID = -996875449180953006L;

    //编号
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //商品编号
    @Column
    private String commodityId;
    //商品数量
    @Column
    private int num;


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
