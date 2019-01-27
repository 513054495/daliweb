package com.code90.daliweb.service;

import com.code90.daliweb.domain.LearnTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 学习主题数据库操作接口
 * @author Ray Lin
 * @create 2018-09-25 23:43
 **/
public interface LearnTopicService extends JpaRepository<LearnTopic,Integer>,JpaSpecificationExecutor<LearnTopic> {
    @Query("select l from LearnTopic l where l.id=?1")
    LearnTopic getLearnTopicById(String id);
    @Query("select l from LearnTopic l  order by l.level desc,l.createTime ASC ")
    List<LearnTopic> getLearnTopics();
    @Query("select l from LearnTopic l where l.status=?1  order by l.level desc,l.createTime ASC ")
    List<LearnTopic> getLearnTopicsByState(int state);
    @Query("select max(l.level) from LearnTopic l ")
    int getMaxLevel();
    @Query("select l from LearnTopic l where l.parentId is null order by l.level desc")
    List<LearnTopic> getRootLearnTopic();
    @Query("select l from LearnTopic l where l.parentId=?1  order by l.level desc")
    List<LearnTopic> getLearnTopicByParentId(String id);
    @Query("select l from LearnTopic l where l.parentId is null and l.status=1 order by l.level desc")
    List<LearnTopic> getRootLearnTopicNoStop();
    @Query("select l from LearnTopic l where l.parentId=?1 and l.status=1 order by l.level desc")
    List<LearnTopic> getLearnTopicByParentIdNoStop(String id);
}
