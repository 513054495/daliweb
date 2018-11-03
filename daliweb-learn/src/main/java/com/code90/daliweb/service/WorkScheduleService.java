package com.code90.daliweb.service;

import com.code90.daliweb.domain.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 作业进度操作数据库接口
 * @author Ray Lin
 * @create 2018-10-11 22:39
 **/
public interface WorkScheduleService extends JpaRepository<WorkSchedule,Integer>,JpaSpecificationExecutor<WorkSchedule> {
    @Query("select w from WorkSchedule w where w.id=?1")
    WorkSchedule getById(String id);
    @Query("select w from WorkSchedule w where w.workId=?1 and w.createBy=?2")
    WorkSchedule getAllByWorkIdAndCreateBy(String workId, String createBy);
}
