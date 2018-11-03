package com.code90.daliweb.server;

import com.code90.daliweb.domain.SubjectSchedule;

/**
 * 答题进度接口
 * @author Ray Lin
 * @create 2018-09-24 15:05
 **/
public interface SubjectScheduleServer extends BaseServer {
    /**
     * 根据用户帐号获取答题进度
     * @param userCode 用户帐号
     * @return 答题进度
     */
    SubjectSchedule getScheduleByUserCode(String userCode);

}
