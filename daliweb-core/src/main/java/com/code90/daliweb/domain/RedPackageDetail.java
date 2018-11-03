package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 红包记录实体类
 * @author Ray Lin
 * @create 2018-10-22 22:28
 **/
@Entity
@Table(name = "sys_redpackage_detail")
public class RedPackageDetail extends BaseDomain {
    private static final long serialVersionUID = -8243494287720310928L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //获取途径（0.分享答题，1.自己答题，2.订单使用）
    @Column
    private int type;
    //获得金额
    @Column
    private double money;
    //答题人
    @Column
    private String answerUserCode;
    //订单号
    @Column
    private String orderNo;
    //分享码
    @Column
    private String shareCode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getAnswerUserCode() {
        return answerUserCode;
    }

    public void setAnswerUserCode(String answerUserCode) {
        this.answerUserCode = answerUserCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
