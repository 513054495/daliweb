package com.code90.daliweb.request.learn;

import com.code90.daliweb.request.CommonRequest;


/**
 * 考试详情新增请求类
 * @author Ray Lin
 * @create 2018-10-11 22:55
 **/
public class WorkDetailSaveReq extends CommonRequest {
    private String workScheduleId;
    private int subjectId;
    private String answer;
    private String createBy;

    public String getWorkScheduleId() {
        return workScheduleId;
    }

    public void setWorkScheduleId(String workScheduleId) {
        this.workScheduleId = workScheduleId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
