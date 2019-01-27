package com.code90.daliweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品分类
 * @author Ray Lin
 * @create 2019-01-16 22:01
 **/
@Entity
@Table(name = "sys_commodity_type")
public class CommodityType extends BaseDomain {
    private static final long serialVersionUID = -795433645740020173L;
    @Id
    private int id;
    @Column
    private int parentId;
    //分类名称
    @Column
    private String name;
    //分类描述
    @Column
    private String description;
    //分类图标
    @Column
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
