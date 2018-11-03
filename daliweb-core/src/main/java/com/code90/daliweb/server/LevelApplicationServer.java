package com.code90.daliweb.server;

import com.code90.daliweb.domain.LevelApplication;
import com.code90.daliweb.request.exchange.LevelApplicationSearchReq;

import java.util.List;

/**
 * 考级申请服务接口
 * @author Ray Lin
 * @create 2018-10-16 23:27
 **/
public interface LevelApplicationServer extends BaseServer  {
    /**
     * 获取全部考级申请
     * @param req 查询条件
     * @return 全部考级申请
     */
    List<LevelApplication> getAll(LevelApplicationSearchReq req);

    /**
     * 分页获取全部考级申请
     * @param page 页码
     * @param pageSize 页大小
     * @param req 查询条件
     * @return 全部考级申请
     */
    List<LevelApplication> findLevelApplicationCriteria(int page, int pageSize, LevelApplicationSearchReq req);

    /**
     * 根据用户获取考级申请
     * @param userCode
     * @return 考级申请
     */
    List<LevelApplication> getByUserCode(String userCode);
}
