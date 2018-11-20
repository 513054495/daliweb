package com.code90.daliweb.service;

import com.code90.daliweb.domain.WorkDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 作业详情操作数据库接口
 * @author Ray Lin
 * @create 2018-10-11 23:01
 **/
public interface WorkDetailService extends JpaRepository<WorkDetail,Integer>,JpaSpecificationExecutor<WorkDetail> {
    @Query("select w from WorkDetail w where w.id=?1")
    WorkDetail getById(String id);
    @Query("select w from WorkDetail w where w.workScheduleId=?1 and w.createBy=?2 order by w.createTime asc")
    List<WorkDetail> getAllByWorkScheduleIdAndCreateBy(String workScheduleId, String createBy);
    @Query("select w from WorkDetail w where w.workScheduleId=?1 and w.createBy=?2 and w.subjectId=?3 order by w.createTime asc")
    WorkDetail getAllByWorkScheduleIdAndCreateByAndSubjectId(String id, String userCode, int subjectId);
    @Query("select w from WorkDetail w where w.workScheduleId=?1")
    List<WorkDetail> getAllByWorkScheduleId(String id);
}
