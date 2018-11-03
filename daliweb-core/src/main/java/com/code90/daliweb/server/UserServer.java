package com.code90.daliweb.server;

import com.code90.daliweb.domain.Recommend;
import com.code90.daliweb.domain.User;
import com.code90.daliweb.request.user.UserSearchReq;

import java.util.List;

/**
 * 用户服务接口
 * @author Ray
 * @create 2018-05-28 0:14
 **/
public interface UserServer extends BaseServer {

    /**
     * 根据用户帐号查找用户
     * @param userCode 用户帐号
     * @return 用户信息
     */
    User getUserByUserCode(String userCode);


    /**
     * 分页根据条件查询全部用户
     * @param page 页码
     * @param size 页大小
     * @param req 查询条件
     * @return 用户信息
     */
    List<User> findUserCriteria(Integer page, Integer size, UserSearchReq req);

    /**
     * 获取全部用户列表
     * @return 用户列表
     */
    List<User> getAll(UserSearchReq req);

    /**
     * 分页根据条件查询全部用户(荣誉板块)
     * @param page 页码
     * @param pageSize 页大小
     * @param req 查询条件
     * @return 用户信息
     */
    List<User> findUserCriteriaByHonor(int page, int pageSize, UserSearchReq req);

    /**
     * 根据分享码获取推荐用户
     * @param shareCode
     * @return 推荐用户
     */
    User getUserByShareCode(String shareCode);

    /**
     * 保存推荐关系
     * @param recommend
     */
    void saveRecommend(Recommend recommend);

    /**
     * 根据用户获取推荐用户列表
     * @param userCode
     * @return 列表
     */
    List<Recommend> getRecommendByUserCode(String userCode);

    /**
     * 根据用户帐号获取推荐代理帐号
     * @param userCode
     * @return 推荐代理帐号
     */
    String getRecommendByCreateBy(String userCode);
}
