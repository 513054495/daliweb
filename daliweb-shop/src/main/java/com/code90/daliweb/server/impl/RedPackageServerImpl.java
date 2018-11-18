package com.code90.daliweb.server.impl;

import com.code90.daliweb.domain.RedPackageDetail;
import com.code90.daliweb.server.RedPackageServer;
import com.code90.daliweb.service.RedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 红包服务接口实现类
 * @author Ray Lin
 * @create 2018-10-23 0:27
 **/
@Service("redPackageServer")
public class RedPackageServerImpl implements RedPackageServer {
    @Autowired
    private RedPackageService redPackageService;
    @Override
    public void save(Object object) {
        redPackageService.save((RedPackageDetail)object);
    }

    @Override
    public void delete(Object object) {
        redPackageService.delete((RedPackageDetail)object);
    }

    @Override
    public Object getObjectById(String id) {
        return redPackageService.getRedPackageDetailById(id);
    }

    @Override
    public double getTotalMoneyByUserCode(String userCode) {
        return redPackageService.getTotalMoneyByUserCode(userCode);
    }

    @Override
    public List<RedPackageDetail> getAllByUserCode(String userCode) {
        return redPackageService.getAllByUserCode(userCode);
    }

    @Override
    public RedPackageDetail getRedPackageDetail(String userCode, String shareCode) {
        return redPackageService.getRedPackageDetail(userCode,shareCode);
    }

    @Override
    public double getAllRedPackage() {
        return redPackageService.getAllRedPackage();
    }
}
