package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.LearnTopic;
import com.code90.daliweb.server.LearnTopicServer;
import com.code90.daliweb.service.LearnTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学习主题服务接口实现类
 * @author Ray Lin
 * @create 2018-09-25 23:41
 **/
@Service("learnTopicServer")
public class LearnTopicServerImpl implements LearnTopicServer {

    @Autowired
    private LearnTopicService learnTopicService;

    @Override
    public void save(Object learnTopic) {
        learnTopicService.save((LearnTopic) learnTopic);
    }

    @Override
    public Object getObjectById(String id) {
        return learnTopicService.getLearnTopicById(id);
    }

    @Override
    public void delete(Object learnTopic) {
        learnTopicService.delete((LearnTopic)learnTopic);
    }

    @Override
    public int getMaxLevel() {
        return learnTopicService.getMaxLevel();
    }

    @Override
    public List<LearnTopic> getRootLearnTopic(int isShow) {
        if(isShow==0){
            return learnTopicService.getRootLearnTopicNoStop();
        }else{
            return learnTopicService.getRootLearnTopic();
        }

    }

    @Override
    public List<LearnTopic> getLearnTopicByParentId(String id, int isShow) {
        if(isShow==0){
            return learnTopicService.getLearnTopicByParentIdNoStop(id);
        }else{
            return learnTopicService.getLearnTopicByParentId(id);
        }
    }


}
