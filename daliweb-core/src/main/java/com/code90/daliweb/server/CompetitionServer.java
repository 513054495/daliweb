package com.code90.daliweb.server;

import com.code90.daliweb.domain.Competition;
import com.code90.daliweb.request.exchange.CompetitionSearchReq;

import java.util.List;

/**
 * 竞赛服务接口
 * @author Ray Lin
 * @create 2018-10-18 0:58
 **/
public interface CompetitionServer extends BaseServer {
    /**
     * 获取全部竞赛
     * @param req 查询条件
     * @return 竞赛列表
     */
    List<Competition> getAll(CompetitionSearchReq req);

    /**
     * 分页获取全部竞赛
     * @param page 页码
     * @param pageSize 页大小
     * @param req 查询条件
     * @return 竞赛列表
     */
    List<Competition> findCompetitionCriteria(int page, int pageSize, CompetitionSearchReq req);
    /**
     * 获取全部竞赛
     * @return 竞赛列表
     */
    List<Competition> getAll();

    /**
     * 获取全部开始的竞赛
     * @return 全部开始的竞赛
     */
    List<Competition> getAllStartCompetition();
}
