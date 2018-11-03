package com.code90.daliweb.server;

import com.code90.daliweb.domain.CompetitionDetail;
import com.code90.daliweb.request.exchange.CompetitionDetailSearchReq;

import java.util.List;

/**
 * 竞赛详情服务接口
 * @author Ray Lin
 * @create 2018-10-18 0:59
 **/
public interface CompetitionDetailServer extends BaseServer {
    /**
     * 获取全部竞赛详情
     * @param req 查询条件
     * @return 竞赛详情列表
     */
    List<CompetitionDetail> getAll(CompetitionDetailSearchReq req);
    /**
     * 分页获取全部竞赛详情
     * @param page 页码
     * @param pageSize 页大小
     * @param req 查询条件
     * @return 竞赛详情列表
     */
    List<CompetitionDetail> findCompetitionDetailCriteria(int page, int pageSize, CompetitionDetailSearchReq req);

    /**
     * 根据竞赛编号和用户帐号获取详情
     * @param id
     * @param userCode
     * @return 竞赛详情
     */
    CompetitionDetail getByCompetitionIdAndUserCode(String id, String userCode);

    /**
     * 根据竞赛编号获取竞赛详细
     * @param id
     * @return 竞赛详细
     */
    List<CompetitionDetail> getDetailByCompetitionId(String id);
}
