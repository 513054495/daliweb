package com.code90.daliweb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 * @author Ray
 * @create 2018-05-28 0:14
 **/
@MappedSuperclass
public class BaseDomain implements Serializable {
    @Column
    //版本号
    public int version;
    @Column(name = "create_by")
    //创建人
    public String createBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME",updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    //创建时间
    public Date createTime;
    @Column(name="MODIFY_TIME")
    @org.hibernate.annotations.UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    //修改时间
    public Date modifyTime;
    @Column(name = "lastmodified_by")
    //修改人
    public String lastmodifiedBy;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    public void setLastmodifiedBy(String lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
    }
}
