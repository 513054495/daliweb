package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.SubjectSchedule;
import com.code90.daliweb.server.SubjectScheduleServer;
import com.code90.daliweb.service.SubjectScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 答题进度接口实现类
 * @author Ray Lin
 * @create 2018-09-24 15:08
 **/
@Service("subjectScheduleServer")
public class SubjectScheduleServerImpl implements SubjectScheduleServer {

    @Autowired
    private SubjectScheduleService subjectScheduleService;

    @Override
    public SubjectSchedule getScheduleByUserCode(String userCode) {
        return subjectScheduleService.getScheduleByUserCode(userCode);
    }

    @Override
    public void save(Object subjectSchedule) {
        subjectScheduleService.save((SubjectSchedule) subjectSchedule);
    }

    @Override
    public void delete(Object object) {
        subjectScheduleService.delete((SubjectSchedule)object);
    }

    @Override
    public Object getObjectById(String id) {
        return null;
    }
}
