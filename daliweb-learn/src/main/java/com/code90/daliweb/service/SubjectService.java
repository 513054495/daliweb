package com.code90.daliweb.service;


import com.code90.daliweb.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 题目数据库服务接口
 * @author Ray
 * @create 2017-05-21 23:57
 */
public interface SubjectService extends JpaRepository<Subject,Integer>,JpaSpecificationExecutor<Subject> {
    @Query("select s from Subject s where s.id=?1")
    Subject getSubjectById(int id);
    @Query("select max(s.id) from Subject s")
    int getMaxSubject();
    @Query("select s from Subject s where  s.type<>3 and s.type<>4")
    List<Subject> getAllByAnwser();
    @Query("select min(s.id) from Subject s where  s.type<>3 and s.type<>4")
    int getMixSubjectBynswer();
    @Query("select s from Subject s where  s.type<>3 and s.type<>4 and s.subjectType=0")
    List<Subject> getAllByRandom();
    @Query("select max(s.id) from Subject s where  s.type<>3 and s.type<>4")
    int getMaxSubjectByExam();
}
