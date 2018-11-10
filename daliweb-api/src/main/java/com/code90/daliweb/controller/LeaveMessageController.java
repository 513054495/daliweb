package com.code90.daliweb.controller;

import com.code90.daliweb.domain.LeaveMessage;
import com.code90.daliweb.request.exchange.LeaveMessageChangeReq;
import com.code90.daliweb.request.exchange.LeaveMessageSaveReq;
import com.code90.daliweb.request.exchange.LeaveMessageSearchReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.LeaveMessageServer;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 留言控制类
 * @author Ray Lin
 * @create 2018-11-08 22:16
 **/
@RestController
@RequestMapping("/daliweb/leaveMessage")
public class LeaveMessageController {
    private static final Logger logger=LoggerFactory.getLogger(LeaveMessageController.class);
    @Autowired
    private LeaveMessageServer leaveMessageServer;

    /**
     * 新增留言
     * @param req 留言信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addLeaveMessage",method = RequestMethod.POST)
    public CommonResponse addLeaveMessage(@RequestBody LeaveMessageSaveReq req){
        try {
            LeaveMessage leaveMessage=new LeaveMessage();
            BeanUtils.copyProperties(req,leaveMessage);
            leaveMessage.createBy=req.getCreateBy();
            leaveMessageServer.save(leaveMessage);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改留言
     * @param req 留言内容
     * @return 修改结果
     */
    @RequestMapping(value="/editLeaveMessage",method = RequestMethod.POST)
    public CommonResponse editLeaveMessage(@RequestBody LeaveMessageChangeReq req){
        try {
            LeaveMessage leaveMessage=(LeaveMessage) leaveMessageServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,leaveMessage);
            leaveMessageServer.save(leaveMessage);
            logger.info("留言修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改留言状态
     * @param ids 留言编号
     * @param status 状态
     * @param reply 回复
     * @param modifiedBy 回复人
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status, String reply, String modifiedBy){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    LeaveMessage leaveMessage = (LeaveMessage) leaveMessageServer.getObjectById(id);
                    leaveMessage.setStatus(status);
                    if (!StringUtil.isEmpty(reply)) {
                        leaveMessage.setReply(reply);
                    }
                    if (!StringUtil.isEmpty(modifiedBy)) {
                        leaveMessage.lastmodifiedBy = modifiedBy;
                    }
                    leaveMessageServer.save(leaveMessage);
                }
                logger.info("留言修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("留言修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取留言
     * @param id 编号
     * @return 留言
     */
    @RequestMapping(value="/getLeaveMessageById",method=RequestMethod.GET)
    public CommonResponse getLeaveMessageById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        LeaveMessage leaveMessage=(LeaveMessage) leaveMessageServer.getObjectById(id);
        if(leaveMessage!=null){
            logger.info("获取成功");
            response.addNewDate("info",leaveMessage);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败",2);
        }
    }

    /**
     * 根据用户获取留言
     * @param userCode 编号
     * @return 留言
     */
    @RequestMapping(value="/getLeaveMessageByUserCode",method=RequestMethod.GET)
    public CommonResponse getLeaveMessageByUserCode(@RequestParam("userCode")String userCode){
        CommonResponse response=new CommonResponse("获取成功");
        List<LeaveMessage> leaveMessages=leaveMessageServer.getLeaveMessageByUserCode(userCode);
        logger.info("获取成功");
        response.addNewDate("info",leaveMessages);
        return response;
    }

    /**
     * 删除留言
     * @param ids 留言编号
     * @return 删除结果
     */
    @RequestMapping(value="/delLeaveMessage",method = RequestMethod.DELETE)
    public CommonResponse delLeaveMessage(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    LeaveMessage leaveMessage=(LeaveMessage) leaveMessageServer.getObjectById(id);
                    leaveMessageServer.delete(leaveMessage);
                }
                logger.info("留言删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，留言编号不能为空");
                return new CommonResponse("删除失败，留言编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询留言
     * @param req 分页条件
     * @return 留言列表
     */
    @RequestMapping(value="/getLeaveMessages",method=RequestMethod.GET)
    public CommonResponse getLeaveMessages(LeaveMessageSearchReq req){
        try{
            int page=req.getPage()==0?0:req.getPage()-1;
            int pageSize=req.getPageSize()==0?10:req.getPageSize();
            List<LeaveMessage> allLeaveMessage=leaveMessageServer.getAll(req);
            int total=allLeaveMessage.size();
            int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
            List<LeaveMessage> leaveMessages=leaveMessageServer.findLeaveMessageCriteria(page,pageSize,req);
            logger.info("获取成功");
            CommonResponse response= new CommonResponse("获取成功","info",leaveMessages);
            response.addNewDate("pageNum",page+1);
            response.addNewDate("pageSize",pageSize);
            response.addNewDate("total",total);
            response.addNewDate("totalPage",totalPage);
            return response;
        }catch (Exception e){
            logger.info("获取失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

}
