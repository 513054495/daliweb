package com.code90.daliweb.server;

import com.code90.daliweb.domain.Forum;
import com.code90.daliweb.domain.ForumDetail;
import com.code90.daliweb.request.exchange.ForumSearchReq;

import java.util.List;

/**
 * 论坛服务接口
 * @author Ray Lin
 * @create 2018-11-12 0:37
 **/
public interface ForumServer extends BaseServer {
    /**
     * 保存论坛详情
     * @param forumDetail
     */
    void saveDetail(ForumDetail forumDetail);

    /**
     * 根据编号获取论坛详情
     * @param id
     * @return 论坛详情
     */
    ForumDetail getForumDetailById(String id);

    /**
     * 根据论坛编号获取论坛详情
     * @param id
     * @return
     */
    List<ForumDetail> getForumDetailByForumId(String id);

    /**
     * 删除论坛详情
     * @param forumDetail
     */
    void deleteDetail(ForumDetail forumDetail);

    /**
     * 获取全部论坛
     * @param req
     * @return 论坛列表
     */
    List<Forum> getAll(ForumSearchReq req);

    /**
     * 分页获取全部论坛
     * @param page
     * @param pageSize
     * @param req
     * @return 论坛列表
     */
    List<Forum> findForumCriteria(int page, int pageSize, ForumSearchReq req);
}
