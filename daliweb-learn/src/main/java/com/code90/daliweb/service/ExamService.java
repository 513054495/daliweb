package com.code90.daliweb.service;

import com.code90.daliweb.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 考试数据库操作接口
 * @author Ray Lin
 * @create 2018-09-25 23:43
 **/
public interface ExamService extends JpaRepository<Exam,Integer>,JpaSpecificationExecutor<Exam> {
    @Query("select e from Exam e where e.id=?1")
    Object getExamById(String id);
}
