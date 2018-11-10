package com.code90.daliweb.service;

import com.code90.daliweb.domain.AnnouncementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 公告详情操作数据库服务接口
 *
 * @author Ray Lin
 * @create 2018-11-11 0:45
 **/
public interface AnnouncementDetailService extends JpaRepository<AnnouncementDetail,Integer>,JpaSpecificationExecutor<AnnouncementDetail> {
    @Query("select a from AnnouncementDetail a where a.announcementId=?1")
    List<AnnouncementDetail> getAnnouncementDetailByAnnouncementId(String id);
    @Query("select a from AnnouncementDetail a where a.announcementId=?1 and a.createBy=?2")
    AnnouncementDetail getAnnouncementDetailByAnnouncementIdAndUserCode(String id, String userCode);
}
