package com.code90.daliweb.service;

import com.code90.daliweb.domain.LearnContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 学习内容数据库操作接口
 * @author Ray Lin
 * @create 2018-09-25 23:43
 **/
public interface LearnContentService extends JpaRepository<LearnContent,Integer>,JpaSpecificationExecutor<LearnContent> {
    @Query("select l from LearnContent l where l.id=?1")
    LearnContent getLearnContentById(String id);
    @Query("select max(l.level) from LearnContent l ")
    int getMaxLevel();
    @Query("select l from LearnContent l where l.topicId=?1")
    LearnContent getLearnContentByTopicId(String id);
}
