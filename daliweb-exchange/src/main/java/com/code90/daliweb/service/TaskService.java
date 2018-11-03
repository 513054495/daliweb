package com.code90.daliweb.service;

import com.code90.daliweb.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 任务数据库服务接口
 * @author Ray Lin
 * @create 2018-10-03 1:56
 **/
public interface TaskService extends JpaRepository<Task,Integer>,JpaSpecificationExecutor<Task> {
    @Query("select t from Task t where t.id=?1")
    Task getTaskById(String id);
    @Query("select t from Task t where t.status=1")
    List<Task> getAllStartTask();
}
