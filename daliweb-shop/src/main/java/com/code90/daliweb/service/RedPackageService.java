package com.code90.daliweb.service;

import com.code90.daliweb.domain.RedPackageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 红包数据库操作接口
 * @author Ray Lin
 * @create 2018-10-23 0:29
 **/
public interface RedPackageService extends JpaRepository<RedPackageDetail,Integer>,JpaSpecificationExecutor<RedPackageDetail> {
    @Query("select r from RedPackageDetail r where r.id=?1 order by r.createTime desc")
    RedPackageDetail getRedPackageDetailById(String id);
    @Query("select coalesce(sum(r.money),0) from RedPackageDetail r where r.createBy=?1 order by r.createTime desc")
    double getTotalMoneyByUserCode(String userCode);
    @Query("select r from RedPackageDetail r where r.createBy=?1 order by r.createTime desc")
    List<RedPackageDetail> getAllByUserCode(String userCode);
    @Query("select r from RedPackageDetail r where r.createBy=?1 and r.shareCode=?2 order by r.createTime desc")
    RedPackageDetail getRedPackageDetail(String userCode, String shareCode);
    @Query("select coalesce(sum(r.money),0) from RedPackageDetail r")
    int getAllRedPackage();
}
