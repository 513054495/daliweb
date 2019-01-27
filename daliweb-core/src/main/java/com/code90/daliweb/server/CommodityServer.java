package com.code90.daliweb.server;

import com.code90.daliweb.domain.Commodity;
import com.code90.daliweb.domain.CommodityNorm;
import com.code90.daliweb.domain.CommodityType;
import com.code90.daliweb.request.shop.CommoditySearchReq;

import java.util.List;

/**
 * 商品服务接口
 * @author Ray Lin
 * @create 2018-09-12 22:22
 **/
public interface CommodityServer extends BaseServer {

    /**
     * 根据商品号查找商品,包括删除
     * @param id 商品号
     * @return 商品信息
     */
    Commodity getCommodityAndDeleteById(String id);

    /**
     * 分页根据条件查询全部商品
     * @param page 页码
     * @param size 页大小
     * @param req 查询条件
     * @return 商品信息
     */
    List<Commodity> findCommodityCriteria(Integer page, Integer size, CommoditySearchReq req);

    /**
     * 获取全部商品列表
     * @return 商品列表
     */
    List<Commodity> getAll(CommoditySearchReq req);

    /**
     * 获取全部商品
     * @return 全部商品
     */
    List<Commodity> getAllCommodity();

    int getMaxCommodityTypeId();

    void saveCommodityType(CommodityType commodityType);

    CommodityType getCommodityTypeById(int id);

    void delCommodityType(CommodityType commodityType);

    List<CommodityType> getChildTypeByParentId(int id);

    List<CommodityType> getRootCommodityType();

    void saveCommodityNorms(CommodityNorm commodityNorm);

    List<CommodityNorm> getCommodityNormByCommodityId(String id);

    void delCommodityNorm(CommodityNorm commodityNorm);

    CommodityNorm getCommodityNormById(String normId);

    int getMaxLevel();
}
