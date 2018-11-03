package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.Rules;
import com.code90.daliweb.server.RuleServer;
import com.code90.daliweb.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 规则服务接口实现类
 * @author Ray Lin
 * @create 2018-10-22 22:52
 **/
@Service("ruleServer")
public class RuleServerImpl implements RuleServer {
    @Autowired
    private RuleService ruleService;
    @Override
    public void save(Object object) {
        ruleService.save((Rules)object);
    }

    @Override
    public void delete(Object object) {
        ruleService.delete((Rules)object);
    }

    @Override
    public Object getObjectById(String id) {
        return ruleService.getRuleById(Integer.parseInt(id));
    }
}
