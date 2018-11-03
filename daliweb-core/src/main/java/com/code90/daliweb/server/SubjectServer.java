package com.code90.daliweb.server;

import com.code90.daliweb.domain.Subject;
import com.code90.daliweb.request.learn.SubjectSearchReq;

import java.util.List;

/**
 * 题目服务接口
 * @author Ray Lin
 * @create 2018-09-24 1:50
 **/
public interface SubjectServer extends BaseServer {


    /**
     * 获取全部题目列表
     * @return 题目列表
     */
    List<Subject> getAll(SubjectSearchReq req);
    /**
     * 分页根据条件查询全部题目
     * @param page 页码
     * @param pageSize 页大小
     * @param req 查询条件
     * @return 题目信息
     */
    List<Subject> findSubjectCriteria(int page, int pageSize, SubjectSearchReq req);

    /**
     * 删除题目
     * @param subject 题目对象
     */
    void delete(Subject subject);

    /**
     * 查找题目最大编号
     * @return 题目最大编号
     */
    int getMaxSubject();

    /**
     * 获取所有非填空题、非问答题
     * @return 题目
     */
    List<Subject> getAllByAnwser();
    /**
     * 获取所有非填空题、非问答题的最小编号
     * @return 最小编号
     */
    int getMixSubjectByAnswer();
    /**
     * 获取所有初级的非填空题、非问答题
     * @return 题目
     */
    List<Subject> getAllByRandom();
    /**
     * 获取所有非填空题、非问答题的最大编号
     * @return 最小编号
     */
    int getMaxSubjectByExam();
}
