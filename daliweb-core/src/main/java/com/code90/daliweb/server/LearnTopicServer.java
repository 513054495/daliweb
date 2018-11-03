package com.code90.daliweb.server;

import com.code90.daliweb.domain.LearnTopic;

import java.util.List;

/**
 * 学习主题服务接口
 * @author Ray Lin
 * @create 2018-09-25 23:40
 **/
public interface LearnTopicServer extends BaseServer {
    /**
     * 获取最大等级
     * @return 最大等级
     */
    int getMaxLevel();

    /**
     * 获取所有父级主题
     * @return 所有父级主题
     * @param isShow
     */
    List<LearnTopic> getRootLearnTopic(int isShow);

    /**
     * 根据父级编号获取子主题
     * @param id
     * @param isShow
     * @return 子主题
     */
    List<LearnTopic> getLearnTopicByParentId(String id, int isShow);
}
