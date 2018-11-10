package com.code90.daliweb.server;

import com.code90.daliweb.domain.LeaveMessage;
import com.code90.daliweb.request.exchange.LeaveMessageSearchReq;

import java.util.List;

/**
 * 留言服务接口
 * @author Ray Lin
 * @create 2018-11-08 22:25
 **/
public interface LeaveMessageServer extends BaseServer {
    /**
     * 分页获取留言
     * @param req
     * @return 留言列表
     */
    List<LeaveMessage> getAll(LeaveMessageSearchReq req);
    /**
     * 分页获取留言（条件）
     * @param req
     * @return 留言列表
     */
    List<LeaveMessage> findLeaveMessageCriteria(int page, int pageSize, LeaveMessageSearchReq req);

    /**
     * 根据用户获取留言
     * @param userCode
     * @return
     */
    List<LeaveMessage> getLeaveMessageByUserCode(String userCode);
}
