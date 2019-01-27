package com.code90.daliweb.service;

import com.code90.daliweb.domain.CommodityNorm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品规格数据库操作类
 * @author Ray Lin
 * @create 2019-01-19 14:38
 **/
public interface CommodityNormService extends JpaRepository<CommodityNorm,Integer>,JpaSpecificationExecutor<CommodityNorm> {
    @Query("select n from CommodityNorm n where n.commodityId=?1 and n.isDelete=0")
    List<CommodityNorm> getCommodityNormByCommodityId(String id);
    @Query("select n from CommodityNorm n where n.id=?1 ")
    CommodityNorm getCommodityNormById(String normId);
}
