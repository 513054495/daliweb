package com.code90.daliweb.server;

import com.code90.daliweb.domain.LearnContent;
import com.code90.daliweb.request.learn.LearnContentSearchReq;

import java.util.List;

/**
 * 学习内容服务接口
 * @author Ray Lin
 * @create 2018-10-01 16:14
 **/
public interface LearnContentServer extends BaseServer {
    /**
     * 获取最大等级
     * @return 最大等级
     */
    int getMaxLevel();
    /**
     * 获取全部内容
     * @param req 获取条件
     * @return 内容列表
     */
    List<LearnContent> getAll(LearnContentSearchReq req);
    /**
     * 分页获取全部内容
     * @param req 获取条件
     * @return 内容列表
     */
    List<LearnContent> findLearnContentCriteria(int page, int pageSize, LearnContentSearchReq req);

    /**
     * 根据父主题获取学习内容
     * @param id
     * @return 获取学习内容
     */
    LearnContent getLearnContentByTopicId(String id);
}
