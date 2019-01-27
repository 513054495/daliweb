package com.code90.daliweb.controller;


import com.code90.daliweb.domain.LearnTopic;
import com.code90.daliweb.domain.LearnTopicVo;
import com.code90.daliweb.domain.TypeListVo;
import com.code90.daliweb.request.learn.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.LearnTopicServer;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 学习主题控制类
 * @author Ray Lin
 * @create 2018-09-25 23:29
 **/
@RestController
@RequestMapping("/daliweb/learnTopic")
public class LearnTopicController {

    private static final Logger logger=LoggerFactory.getLogger(LearnTopicController.class);
    @Autowired
    private LearnTopicServer learnTopicServer;

    /**
     * 新增
     * @param req 主题信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addTopic",method = RequestMethod.POST)
    public CommonResponse addTopic(@RequestBody LearnTopicSaveReq req){
        try {
            LearnTopic learnTopic=new LearnTopic();
            BeanUtils.copyProperties(req,learnTopic);
            learnTopicServer.save(learnTopic);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改主题
     * @param req 修改主题
     * @return 修改结果
     */
    @RequestMapping(value="/editTopic",method = RequestMethod.POST)
    public CommonResponse editTopic(@RequestBody LearnTopicChangeReq req){
        try {
            LearnTopic learnTopic=(LearnTopic) learnTopicServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,learnTopic);
            learnTopicServer.save(learnTopic);
            logger.info("主题修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改主题
     * @param ids 主题编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids,@RequestParam("status")int status){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    LearnTopic learnTopic = (LearnTopic) learnTopicServer.getObjectById(id);
                    learnTopic.setStatus(status);
                    learnTopicServer.save(learnTopic);
                }
                logger.info("主题修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("主题修改失败");
                return new CommonResponse("修改失败，主题编号不能为空",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 置顶主题
     * @param id 主题编号
     * @param isTop 是否置顶
     * @return 修改结果
     */
    @RequestMapping(value="/topTopic",method = RequestMethod.GET)
    public CommonResponse topTopic(@RequestParam("id")String id,@RequestParam("isTop")int isTop ){
        try {
            LearnTopic learnTopic=(LearnTopic) learnTopicServer.getObjectById(id);
            if(isTop==1) {
                int maxLevel = learnTopicServer.getMaxLevel();
                learnTopic.setLevel(maxLevel + 1);
                learnTopic.setIsTop(1);
                learnTopicServer.save(learnTopic);
                logger.info("主题置顶成功");
                return new CommonResponse("置顶成功");
            }else{
                learnTopic.setLevel(0);
                learnTopic.setIsTop(0);
                learnTopicServer.save(learnTopic);
                logger.info("主题取消置顶成功");
                return new CommonResponse("取消置顶成功");
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取主题
     * @param id 编号
     * @return 主题
     */
    @RequestMapping(value="/getTopicById",method=RequestMethod.GET)
    public CommonResponse getTopicById(@RequestParam("id")String id){
        LearnTopic learnTopic=(LearnTopic) learnTopicServer.getObjectById(id);
        LearnTopicVo learnTopicVo = new LearnTopicVo();
        BeanUtils.copyProperties(learnTopic, learnTopicVo);
        LearnTopic parentTopic=(LearnTopic) learnTopicServer.getObjectById(learnTopic.getParentId());
        if(null!=parentTopic){
            learnTopicVo.setParentName(parentTopic.getTopicName());
        }
        if(learnTopic!=null){
            logger.info("获取成功");
            return new CommonResponse("获取成功","info",learnTopicVo);
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 获取全部主题
     * @return 主题列表
     */
    @RequestMapping(value="/getTopics",method=RequestMethod.GET)
    public CommonResponse getTopics(@RequestParam("isShow")int isShow) {
        try {
            List<Object> learnTopicVos = new ArrayList<>();
            List<LearnTopic> rootLearnTopic = learnTopicServer.getRootLearnTopic(isShow);
            for (LearnTopic learnTopic : rootLearnTopic) {
                List<LearnTopic> childent = learnTopicServer.getLearnTopicByParentId(learnTopic.getId(),isShow);
                if(childent.size()>0){
                    LearnTopicVo learnTopicVo = new LearnTopicVo();
                    BeanUtils.copyProperties(learnTopic, learnTopicVo);
                    learnTopicVo.setChildren(childent);
                    learnTopicVos.add(learnTopicVo);
                }else{
                    learnTopicVos.add(learnTopic);
                }
            }
            logger.info("获取成功");
            return new CommonResponse("获取成功", "info", learnTopicVos);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 删除主题
     * @param ids 主题编号
     * @return 删除结果
     */
    @RequestMapping(value="/delTopic",method = RequestMethod.DELETE)
    public CommonResponse delTopic(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    LearnTopic learnTopic=(LearnTopic) learnTopicServer.getObjectById(id);
                    learnTopicServer.delete(learnTopic);
                }
                logger.info("主题删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，主题编号不能为空");
                return new CommonResponse("删除失败，主题编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }
}
