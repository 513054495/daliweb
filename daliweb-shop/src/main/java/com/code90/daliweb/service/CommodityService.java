package com.code90.daliweb.service;

import com.code90.daliweb.domain.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 商品数据库服务接口
 * @author Ray Lin
 * @create 2018-09-12 21:55
 **/
public interface CommodityService extends JpaRepository<Commodity,Integer>,JpaSpecificationExecutor<Commodity> {
    @Query("select c from Commodity c where c.id=?1 and c.deleted=0 ")
    Commodity getCommodityById(String id);

    @Query("select c from Commodity c where c.id=?1")
    Commodity getCommodityAndDeleteById(String id);
}
