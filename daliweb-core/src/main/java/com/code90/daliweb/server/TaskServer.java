package com.code90.daliweb.server;

import com.code90.daliweb.domain.Task;
import com.code90.daliweb.request.exchange.TaskSearchReq;

import java.util.List;

/**
 * 任务服务接口
 * @author Ray Lin
 * @create 2018-10-03 1:54
 **/
public interface TaskServer extends BaseServer {
    /**
     * 查询全部任务
     * @param req 查询条件
     * @return 任务列表
     */
    List<Task> getAll(TaskSearchReq req);
    /**
     * 分页查询全部任务
     * @param req 查询条件
     * @return 任务列表
     */
    List<Task> findTaskCriteria(int page, int pageSize, TaskSearchReq req);

    /**
     * 获取全部进行中任务
     * @return 任务列表
     */
    List<Task> getAllStartTask();
}
