package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 分类集合实体类
 * @author Ray Lin
 * @create 2018-10-18 1:23
 **/
@Entity
@Table(name = "sys_type_list")
public class TypeList extends BaseDomain{
    private static final long serialVersionUID = 821602590862668215L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    @Column
    private int mainType;
    @Column
    private int subType;
    @Column
    private String typeName;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public int getMainType() {
        return mainType;
    }

    public void setMainType(int mainType) {
        this.mainType = mainType;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
