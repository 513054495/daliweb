package com.code90.daliweb.service;

import com.code90.daliweb.domain.Proxy;
import com.code90.daliweb.domain.ProxyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 代理详情数据库操作类
 * @author Ray Lin
 * @create 2018-10-25 22:56
 **/
public interface ProxyDetailService extends JpaRepository<ProxyDetail,Integer>,JpaSpecificationExecutor<ProxyDetail> {
    @Query("select p from ProxyDetail p where p.orderNo=?1")
    List<ProxyDetail> getProxyDetailByOrderId(String id);
    @Query("select coalesce(sum(p.money),0) from ProxyDetail p where p.createBy=?1 and p.status=1 order by p.createTime desc")
    double getAllMoneyByUserCode(String userCode);
    @Query("select coalesce(sum(p.money),0) from ProxyDetail p where p.status=1")
    double getAllMoney();
    @Query("select substring(p.createTime,1,7),coalesce(sum(p.money),0) from ProxyDetail p where p.status=1 group by substring(p.createTime,1,7)")
    List<Object[]> getProxyDetailByMonth();
}
