package com.code90.daliweb.service;


import com.code90.daliweb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户数据库服务接口
 * @author Ray
 * @create 2017-05-21 23:57
 */
public interface UserService extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {
    @Query("select u from User u where u.userCode=?1 ")
    User getUserByUserCode(String userCode);
    @Query("select u from User u where u.id=?1 ")
    User getUserById(String id);
    @Query("select u from User u where u.shareCode=?1 ")
    User getUserByShareCode(String shareCode);
    @Query("select u from User u where u.wechatCode=?1 ")
    User getUserByWechatCode(String openid);
}
