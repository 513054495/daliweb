package com.code90.daliweb.server;

import com.code90.daliweb.domain.WorkDetail;
import com.code90.daliweb.request.learn.WorkDetailSearchReq;

import java.util.List;

/**
 * 作业详情服务接口
 * @author Ray Lin
 * @create 2018-10-11 22:59
 **/
public interface WorkDetailServer extends BaseServer {
    /**
     * 根据用户进度编号和创建人查询作业详情
     * @param workScheduleId
     * @param createBy
     * @return 作业详情
     */
    List<WorkDetail> getAllByWorkScheduleIdAndCreateBy(String workScheduleId, String createBy);
    /**
     * 获取全部作业详情
     * @param req 分页条件
     * @return 作业详情列表
     */
    List<WorkDetail> getAll(WorkDetailSearchReq req);
    /**
     * 分页获取全部作业详情
     * @param req 分页条件
     * @return 作业详情列表
     */
    List<WorkDetail> findWorkDetailCriteria(int page, int pageSize, WorkDetailSearchReq req);

    /**
     * 根据用户进度编号、创建人、题号查询作业详情
     * @param id 用户进度编号
     * @param userCode 创建人
     * @param subjectId 题号
     * @return 作业详情
     */
    WorkDetail getAllByWorkScheduleIdAndCreateByAndSubjectId(String id, String userCode, int subjectId);
}
