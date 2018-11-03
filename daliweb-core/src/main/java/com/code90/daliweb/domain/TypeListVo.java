package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类集合展现类
 * @author Ray Lin
 * @create 2018-10-18 1:23
 **/
public class TypeListVo {
    private int id;
    private String typeName;
    private List<TypeList> children=new ArrayList<>();

    public List<TypeList> getChildren() {
        return children;
    }

    public void setChildren(List<TypeList> children) {
        this.children = children;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
