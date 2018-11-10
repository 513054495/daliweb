package com.code90.daliweb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 公告详情实体类
 * @author Ray Lin
 * @create 2018-11-11 0:41
 **/
@Entity
@Table(name="sys_announcement_detail")
public class AnnouncementDetail extends BaseDomain {
    private static final long serialVersionUID = -1103250082926573521L;
    @Id
    @GenericGenerator(name="UUIDGENERATE",strategy="uuid2")
    @GeneratedValue(generator="UUIDGENERATE")
    @Column(name="ID",length=36)
    private String id;
    //所属公告
    @Column
    private String announcementId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

}
