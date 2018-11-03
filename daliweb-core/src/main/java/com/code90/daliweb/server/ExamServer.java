package com.code90.daliweb.server;

import com.code90.daliweb.domain.Exam;
import com.code90.daliweb.request.learn.ExamSearchReq;

import java.util.List;

/**
 * 考试服务接口
 * @author Ray Lin
 * @create 2018-10-06 0:41
 **/
public interface ExamServer extends BaseServer {
    /**
     * 查询全部考试
     * @param req 查询条件
     * @return 考试列表
     */
    List<Exam> getAll(ExamSearchReq req);
    /**
     * 分页查询全部考试
     * @param req 查询条件
     * @return 考试列表
     */
    List<Exam> findExamCriteria(int page, int pageSize, ExamSearchReq req);

    /**
     * 无条件查询全部考试
     * @return
     */
    List<Exam> getAll();

    /**
     * 根据用户帐号获取考试列表
     * @param userCode 用户帐号
     * @return 考试列表
     */
    List<Exam> getExamByUserCode(String userCode);

}
