package com.code90.daliweb.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类
 * @author Ray Lin
 * @create 2019-01-16 22:01
 **/

public class CommodityTypeVo extends BaseDomain {
    private static final long serialVersionUID = -2997757284615766604L;
    private int id;
    private int parentId;
    private String name;
    private String description;
    private String icon;
    private List<CommodityType> childTypes=new ArrayList<>();

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

    public List<CommodityType> getChildTypes() {
        return childTypes;
    }

    public void setChildTypes(List<CommodityType> childTypes) {
        this.childTypes = childTypes;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
