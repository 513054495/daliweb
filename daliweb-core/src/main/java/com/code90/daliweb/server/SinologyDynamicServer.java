package com.code90.daliweb.server;

import com.code90.daliweb.domain.SinologyDynamic;
import com.code90.daliweb.request.exchange.SinologyDynamicSearchReq;

import java.util.List;

/**
 * 动态接口
 * @author Ray Lin
 * @create 2019-01-13 22:44
 **/
public interface SinologyDynamicServer extends  BaseServer{

    List<SinologyDynamic> getAll(SinologyDynamicSearchReq req);

    List<SinologyDynamic> findSinologyDynamicCriteria(int page, int pageSize, SinologyDynamicSearchReq req);
}
