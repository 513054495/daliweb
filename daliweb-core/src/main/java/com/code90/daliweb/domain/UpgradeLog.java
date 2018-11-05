package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 升级记录实体类
 * @author Ray Lin
 * @create 2018-11-04 21:52
 **/
@Entity
@Table(name="sys_upgrade_log")
public class UpgradeLog  extends  BaseDomain{
    private static final long serialVersionUID = -3475326483102086437L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    private String id;
    //升级类型
    @Column
    private int type;


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
}
