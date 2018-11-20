package com.code90.daliweb.service;

import com.code90.daliweb.domain.Proxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 代理接口数据库操作接口
 * @author Ray Lin
 * @create 2018-10-23 23:59
 **/
public interface ProxyService extends JpaRepository<Proxy,Integer>,JpaSpecificationExecutor<Proxy> {
    @Query("select p from Proxy p where p.id=?1 and p.status=0")
    Proxy getProxyById(String id);
    @Query("select p from Proxy p where p.createBy=?1 and p.status=0 order by p.createTime desc")
    Proxy getProxyByUserCode(String recommendUserCode);
    @Query("select p from Proxy p where p.type=?1 and p.status=0 ")
    List<Proxy> getProxiesByType(int i);
    @Query("select p from Proxy p where p.status=0")
    List<Proxy> getProxyList();
}
