package com.code90.daliweb.server;

import com.code90.daliweb.domain.TypeList;

import java.util.List;

/**
 * 分类集合服务接口
 * @author Ray Lin
 * @create 2018-10-18 1:28
 **/
public interface TypeListServer extends BaseServer {
    /**
     * 根据主分类获取分类列表
     * @param mainType 主分类
     * @return 分类列表
     */
    List<TypeList> getAllByMainType(int mainType);

    /**
     * 获取最大子分类编号
     * @return 最大编号
     */
    int getMaxSubjectId();

    /**
     * 根据分类编号获取分类名称
     * @param type
     * @return 分类名称
     */
    TypeList getTypeNameBySubjectId(int type);
}
