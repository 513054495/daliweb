package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;

import java.io.Serializable;

/**
 * 考试进度查询请求类
 * @author Ray Lin
 * @create 2018-10-07 21:58
 **/
public class ExamScheduleSearchReq extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -3537787198639254612L;
    private int page;
    private int pageSize;
    private String examId;
    private String createBy;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
