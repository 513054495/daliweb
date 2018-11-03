package com.code90.daliweb.server;

import com.code90.daliweb.domain.ExamSchedule;
import com.code90.daliweb.request.learn.ExamScheduleSearchReq;

import java.util.List;

/**
 * 考试进度服务接口
 * @author Ray Lin
 * @create 2018-10-07 16:30
 **/
public interface ExamScheduleServer extends BaseServer {
    /**
     * 查询全部考试进度
     * @param req 查询条件
     * @return 考试进度列表
     */
    List<ExamSchedule> getAll(ExamScheduleSearchReq req);
    /**
     * 分页查询全部考试进度
     * @param req 查询条件
     * @return 考试进度列表
     */
    List<ExamSchedule> findExamScheduleCriteria(int page, int pageSize, ExamScheduleSearchReq req);

    /**
     * 根据考试编号获取全部考试进度
     * @param id 考试编号
     * @return 全部考试进度
     */
    List<ExamSchedule> getAllByExanId(String id);
    /**
     * 根据考试编号和考试人获取全部考试进度
     * @param id 考试编号
     * @param userCode 考试人
     * @return 全部考试进度
     */
    ExamSchedule getAllByExanIdAndCreateBy(String id, String userCode);
}
