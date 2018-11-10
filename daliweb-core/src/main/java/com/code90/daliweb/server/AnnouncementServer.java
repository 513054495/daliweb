package com.code90.daliweb.server;

import com.code90.daliweb.domain.Announcement;
import com.code90.daliweb.domain.AnnouncementDetail;
import com.code90.daliweb.domain.User;
import com.code90.daliweb.request.exchange.AnnouncementSearchReq;

import java.util.List;

/**
 * 公告服务接口
 * @author Ray Lin
 * @create 2018-11-10 21:12
 **/
public interface AnnouncementServer extends BaseServer{
    /**
     * 根据用户获取公告
     * @param user
     * @return
     */
    List<Announcement> getAnnouncementByUser(User user);

    /**
     * 保存公告详情
     * @param announcementDetail
     */
    void saveDetail(AnnouncementDetail announcementDetail);

    /**
     * 根据公告编号获取公告详情
     * @param id
     * @return 公告详情
     */
    List<AnnouncementDetail> getAnnouncementDetailByAnnouncementId(String id);

    /**
     * 删除公告详情
     * @param announcementDetail
     */
    void deleteDetail(AnnouncementDetail announcementDetail);

    /**
     * 根据公告编号和用户帐号获取公告详情
     * @param id
     * @param userCode
     * @return 公告详情
     */
    AnnouncementDetail getAnnouncementDetailByAnnouncementIdAndUserCode(String id, String userCode);

    /**
     * 分页根据条件查询全部公告
     * @param req 查询条件
     * @return 公告列表
     */
    List<Announcement> getAll(AnnouncementSearchReq req);

    /**
     * 分页根据条件查询全部公告
     * @param page
     * @param pageSize
     * @param req
     * @return 公告列表
     */
    List<Announcement> findAnnouncementCriteria(int page, int pageSize, AnnouncementSearchReq req);
}
