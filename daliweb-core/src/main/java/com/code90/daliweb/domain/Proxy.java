package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 代理实体类
 * @author Ray Lin
 * @create 2018-10-22 21:48
 **/
@Entity
@Table(name = "sys_proxy")
public class Proxy extends BaseDomain {
    private static final long serialVersionUID = -1062917779312429894L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //银行名称
    @Column
    private String bankName;
    //银行卡号
    @Column
    private String bankCode;
    //代理类型
    @Column
    private int type;
    //代理区域名称
    @Column
    private String area;
    //代理区域代码
    @Column
    private String postalCode;
    //状态（0.启用，1.撤销）
    @Column
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
