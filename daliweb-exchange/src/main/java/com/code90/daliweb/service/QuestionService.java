package com.code90.daliweb.service;

import com.code90.daliweb.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 问题数据库服务接口
 *
 * @author Ray Lin
 * @create 2018-10-01 23:43
 **/
public interface QuestionService extends JpaRepository<Question,Integer>,JpaSpecificationExecutor<Question> {
    @Query("select q from Question q where q.id=?1")
    Object getQuestionById(String id);
}
