package com.code90.daliweb.service;

import com.code90.daliweb.domain.SubjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 答题进度数据库操作类
 * @author Ray Lin
 * @create 2018-09-24 15:09
 **/
public interface SubjectScheduleService extends JpaRepository<SubjectSchedule,Integer>,JpaSpecificationExecutor<SubjectSchedule> {
    @Query("select s from SubjectSchedule s where s.createBy=?1")
    SubjectSchedule getScheduleByUserCode(String userCode);
}