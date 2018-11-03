package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 订单详情实体类
 * @author Ray Lin
 * @create 2018-09-15 1:20
 **/
@Entity
@Table(name="sys_order_detail")
public class OrderDetail extends BaseDomain {
    private static final long serialVersionUID = 7278429838932479751L;

    //编号
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    private String id;
    //订单编号
    @Column
    private String orderId;
    //商品编号
    @Column
    private String commodityId;
    //购买数量
    @Column
    private int orderNum;
    //订单详情状态（0-正常，1-退款中，2-退款完成，3-退款失败）
    @Column
    private int status;
    //退款理由
    @Column
    private String refundReason;
    //实际支付金额
    @Column
    private double money;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
