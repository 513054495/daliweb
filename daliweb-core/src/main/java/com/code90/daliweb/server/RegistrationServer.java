package com.code90.daliweb.server;

import com.code90.daliweb.domain.Registration;
import com.code90.daliweb.domain.RegistrationDetail;
import com.code90.daliweb.request.exchange.RegistrationDetailSearchReq;
import com.code90.daliweb.request.exchange.RegistrationSearchReq;

import java.util.List;

/**
 * 报名服务接口
 * @author Ray Lin
 * @create 2018-10-30 23:22
 **/
public interface RegistrationServer extends BaseServer {
    /**
     * 保存报名详情
     * @param registrationDetail
     */
    void saveDetail(RegistrationDetail registrationDetail);

    /**
     * 根据编号获取报名详情
     * @param id
     * @return
     */
    RegistrationDetail getRegistrationDetailById(String id);

    /**
     * 根据编号删除报名详情
     * @param registrationDetail
     */
    void deleteRegistrationDetail(RegistrationDetail registrationDetail);

    /**
     * 获取报名
     * @param req
     * @return 报名列表
     */
    List<Registration> getAll(RegistrationSearchReq req);

    /**
     * 分页获取报名
     * @param req
     * @return 报名列表
     */
    List<Registration> findRegistrationCriteria(int page, int pageSize, RegistrationSearchReq req);

    /**
     * 获取报名详情
     * @param req
     * @return 报名详情列表
     */
    List<RegistrationDetail> getAllDetail(RegistrationDetailSearchReq req);

    /**
     * 分页获取报名详情
     * @param req
     * @return 报名详情列表
     */
    List<RegistrationDetail> findRegistrationDetailCriteria(int page, int pageSize, RegistrationDetailSearchReq req);

    /**
     * 获取所有发布的报名
     * @return 发布的报名
     */
    List<Registration> getAllPublishRegistration();

    /**
     * 根据报名编号和用户帐号获取报名详情
     * @param id
     * @param userCode
     * @return 报名详情
     */
    RegistrationDetail getRegistrationDetailByIdAndCreateBy(String id, String userCode);
}
