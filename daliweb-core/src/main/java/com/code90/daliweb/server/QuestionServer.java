package com.code90.daliweb.server;

import com.code90.daliweb.domain.Question;
import com.code90.daliweb.request.exchange.QuestionSearchReq;

import java.util.List;

/**
 * 问题服务接口
 * @author Ray Lin
 * @create 2018-10-01 23:40
 **/
public interface QuestionServer extends BaseServer {
    /**
     * 获取全部问题
     * @param req 条件
     * @return 问题列表
     */
    List<Question> getAll(QuestionSearchReq req);

    /**
     * 分页获取问题
     * @param page 页码
     * @param pageSize 页大小
     * @param req 条件
     * @return 问题列表
     */
    List<Question> findQuestionCriteria(int page, int pageSize, QuestionSearchReq req);

    /**
     * 根据问题状态获取问题数量
     * @param i
     * @return 问题数量
     */
    int getQuestionByStatus(int i);
}
