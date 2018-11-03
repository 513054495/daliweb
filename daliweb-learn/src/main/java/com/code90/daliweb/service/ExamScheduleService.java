package com.code90.daliweb.service;

import com.code90.daliweb.domain.ExamSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 考试进度数据库服务类
 * @author Ray Lin
 * @create 2018-10-07 16:32
 **/
public interface ExamScheduleService extends JpaRepository<ExamSchedule,Integer>,JpaSpecificationExecutor<ExamSchedule> {
    @Query("select e from ExamSchedule e where e.id=?1")
    Object getExamSchedleById(String id);
    @Query("select e from ExamSchedule e where e.examId=?1 order by e.point desc")
    List<ExamSchedule> getAllByExanId(String id);
    @Query("select e from ExamSchedule e where e.examId=?1 and e.createBy=?2")
    ExamSchedule getAllByExanIdAndCreateBy(String id,String userCode);
}
