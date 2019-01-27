package com.code90.daliweb.service;

import com.code90.daliweb.domain.CommodityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品分类数据库操作类
 *
 * @author Ray Lin
 * @create 2019-01-16 22:19
 **/
public interface CommodityTypeService extends JpaRepository<CommodityType,Integer>,JpaSpecificationExecutor<CommodityType> {
    @Query("select max(c.id) from CommodityType c ")
    int getMaxCommodityTypeId();
    @Query("select c from CommodityType c where c.parentId=?1")
    List<CommodityType> getChildTypeByParentId(int id);
    @Query("select c from CommodityType c where c.parentId=0")
    List<CommodityType> getRootCommodityType();
    @Query("select c from CommodityType c where c.id=?1")
    CommodityType getCommodityTypeById(int id);
}
