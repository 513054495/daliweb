package com.code90.daliweb.server;

import com.code90.daliweb.domain.Commodity;

/**
 * 基础服务类
 * @author Ray
 * @create 2018-05-28 0:14
 **/
public interface BaseServer {
    /**
     * 保存对象
     * @param object 对象
     */
    void save(Object object);

    /**
     * 删除对象
     * @param object 对象
     */
    void delete(Object object);

    /**
     * 根据对象编号查找对象
     * @param id 对象编号
     * @return 对象
     */
    Object getObjectById(String id);
}
