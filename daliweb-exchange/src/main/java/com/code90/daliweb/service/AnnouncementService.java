package com.code90.daliweb.service;

import com.code90.daliweb.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 公告操作数据库接口
 * @author Ray Lin
 * @create 2018-11-10 22:42
 **/
public interface AnnouncementService extends JpaRepository<Announcement,Integer>,JpaSpecificationExecutor<Announcement> {
    @Query("select a from Announcement a where a.id=?1")
    Announcement getAnnouncementById(String id);
    @Query("select a from Announcement a where a.status<>0")
    List<Announcement> getStartAnnouncement();
}
