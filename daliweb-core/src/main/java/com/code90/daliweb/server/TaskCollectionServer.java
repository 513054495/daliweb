package com.code90.daliweb.server;

import com.code90.daliweb.domain.TaskCollection;
import com.code90.daliweb.request.exchange.TaskCollectionSearchReq;

import java.util.List;

/**
 * 任务领取服务接口
 * @author Ray Lin
 * @create 2018-10-03 16:26
 **/
public interface TaskCollectionServer extends BaseServer {
    /**
     * 获取全部领取任务
     * @param req 查询条件
     * @return 领取任务列表
     */
    List<TaskCollection> getAll(TaskCollectionSearchReq req);
    /**
     * 分页获取全部领取任务
     * @param req 查询条件
     * @return 领取任务列表
     */
    List<TaskCollection> findTaskCollectionCriteria(int page, int pageSize, TaskCollectionSearchReq req);

    /**
     * 根据任务编号获取领取成功总数
     * @param taskId 任务编号
     * @return 领取成功总数
     */
    int getCountByTaskId(String taskId);

    /**
     * 根据任务编号和用户编号获取任务领取
     * @param id  任务编号
     * @param userCode 用户编号
     * @return
     */
    TaskCollection getAllByTaskIdAndCreateBy(String id, String userCode);
}
