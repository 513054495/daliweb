package com.code90.daliweb.server;

import com.code90.daliweb.domain.WorkSchedule;
import com.code90.daliweb.request.learn.WorkScheduleSearchReq;

import java.util.List;

/**
 * 作业进度服务接口
 * @author Ray Lin
 * @create 2018-10-11 22:35
 **/
public interface WorkScheduleServer extends BaseServer {
    /**
     * 根据作业编号和作业人获取作业进度
     * @param workId
     * @param createBy
     * @return 进度
     */
    WorkSchedule getAllByWorkIdAndCreateBy(String workId, String createBy);
    /**
     * 获取全部作业进度
     * @param req 分页条件
     * @return 作业进度列表
     */
    List<WorkSchedule> getAll(WorkScheduleSearchReq req);
    /**
     * 分页获取全部作业进度
     * @param req 分页条件
     * @return 作业进度列表
     */
    List<WorkSchedule> findWorkScheduleCriteria(int page, int pageSize, WorkScheduleSearchReq req);

    /**
     * 根据作业编号获取作业进度
     * @param id
     * @return 获取作业进度
     */
    List<WorkSchedule> getAllByWorkId(String id);
}
