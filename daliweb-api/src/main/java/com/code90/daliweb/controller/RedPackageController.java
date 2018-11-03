package com.code90.daliweb.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.code90.daliweb.domain.RedPackageDetail;
import com.code90.daliweb.domain.Rules;
import com.code90.daliweb.request.shop.RedPackageDetailSaveReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.RedPackageServer;
import com.code90.daliweb.server.RuleServer;
import com.code90.daliweb.utils.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 红包控制类
 * @author Ray Lin
 * @create 2018-10-22 23:35
 **/
@RestController
@RequestMapping("/daliweb/redPackage")
public class RedPackageController {
    private static final Logger logger=LoggerFactory.getLogger(RedPackageController.class);
    @Autowired
    private RedPackageServer redPackageServer;
    @Autowired
    private RuleServer ruleServer;

    /**
     * 获取红包
     * @return 红包
     */
    @RequestMapping(value="/getRedPackage",method=RequestMethod.GET)
    public CommonResponse getRedPackage(){
        Rules rules=(Rules)ruleServer.getObjectById(1+"");
        int code=new Random().nextInt(200)+1;
        double money=0.0;
        int l1=rules.getLevel1()*2;
        int l2=rules.getLevel2()*2+l1;
        int l3=rules.getLevel3()*2+l2;
        int l4=rules.getLevel4()*2+l3;
        if(code<=l1){
            money=IdUtils.createRedPackage(0.0,5.0);
        }else if(l1<code&&code<=l2){
            money=IdUtils.createRedPackage(5.0,10.0);
        }else if(l2<code&code<=l3){
            money=IdUtils.createRedPackage(10.0,20.0);
        }else{
            money=IdUtils.createRedPackage(20.0,50.0);
        }
        logger.info("获得随机红包"+money);
        return new CommonResponse("获取成功","info",money);
    }

    /**
     * 新增红包记录
     * @param req 红包信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addRedPackageDetail",method = RequestMethod.POST)
    public CommonResponse addRedPackageDetail(@RequestBody RedPackageDetailSaveReq req){
        try {
            RedPackageDetail redPackageDetail=new RedPackageDetail();
            BeanUtils.copyProperties(req,redPackageDetail);
            redPackageDetail.createBy=req.getCreateBy();
            redPackageServer.save(redPackageDetail);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 根据用户获取红包记录
     * @param userCode 用户帐号
     * @return 红包总数
     */
    @RequestMapping(value="/getRedPackageDetailByUserCode",method=RequestMethod.GET)
    public CommonResponse getRedPackageDetailByUserCode(@RequestParam("userCode")String userCode){
        try{
            double totalMoney=redPackageServer.getTotalMoneyByUserCode(userCode);
            List<RedPackageDetail> redPackageDetails=redPackageServer.getAllByUserCode(userCode);
            CommonResponse commonResponse=new CommonResponse("获取成功","info",redPackageDetails);
            commonResponse.addNewDate("totalMoney",totalMoney);
            return commonResponse;
        }catch (Exception e){
            logger.error("获取红包记录失败，原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 根据用户、分享码获取是否已获取红包
     * @param userCode 用户帐号
     * @param shareCode 分享码
     * @return 红包总数
     */
    @RequestMapping(value="/getRedPackageDetailByUserCodeAndShareCode",method=RequestMethod.GET)
    public CommonResponse getRedPackageDetailByUserCodeAndShareCode(@RequestParam("userCode")String userCode,@RequestParam("shareCode")String shareCode){
        RedPackageDetail redPackageDetail=redPackageServer.getRedPackageDetail(userCode,shareCode);
        if(null==redPackageDetail){
            return new CommonResponse("获取成功");
        }else{
            return new CommonResponse("获取失败",2);
        }
    }
}
