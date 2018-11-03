package com.code90.daliweb.service;

import com.code90.daliweb.domain.TaskCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 任务领取数据库服务接口
 * @author Ray Lin
 * @create 2018-10-03 16:30
 **/
public interface TaskCollectionService extends JpaRepository<TaskCollection,Integer>,JpaSpecificationExecutor<TaskCollection> {
    @Query("select t from TaskCollection  t where t.id=?1")
    TaskCollection getTaskCollectionById(String id);
    @Query("select count(t.taskId) from TaskCollection  t where t.taskId=?1 and t.status=1")
    int getCountByTaskId(String taskId);
    @Query("select t from TaskCollection  t where t.taskId=?1 and t.createBy=?2")
    TaskCollection getAllByTaskIdAndCreateBy(String id, String userCode);
}
