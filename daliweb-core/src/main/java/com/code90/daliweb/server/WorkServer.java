package com.code90.daliweb.server;

import com.code90.daliweb.domain.Work;
import com.code90.daliweb.request.learn.WorkSearchReq;

import java.util.List;

/**
 * 作业服务接口
 * @author Ray Lin
 * @create 2018-10-11 21:55
 **/
public interface WorkServer  extends BaseServer{
    /**
     * 获取全部开始作业
     * @return
     */
    List<Work> getAllStartWork();

    /**
     * 获取全部作业
     * @param req 分页条件
     * @return 作业列表
     */
    List<Work> getAll(WorkSearchReq req);
    /**
     * 分页获取全部作业
     * @param req 分页条件
     * @return 作业列表
     */
    List<Work> findWorkCriteria(int page, int pageSize, WorkSearchReq req);

    /**
     * 根据用户帐号获取全部作业
     * @param userCode 用户帐号
     * @return 作业列表
     */
    List<Work> getObjectByWorkPersons(String userCode);
}
