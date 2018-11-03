package com.code90.daliweb.controller;

import com.code90.daliweb.domain.Rules;
import com.code90.daliweb.request.exchange.QuestionChangeReq;
import com.code90.daliweb.request.shop.ProxyChangeReq;
import com.code90.daliweb.request.shop.RedPackageChangeReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.RuleServer;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 规则控制类
 * @author Ray Lin
 * @create 2018-10-22 22:43
 **/
@RestController
@RequestMapping("/daliweb/rules")
public class RulesController{
    private static final Logger logger=LoggerFactory.getLogger(RulesController.class);
    @Autowired
    private RuleServer ruleServer;
    private static Rules global_rules=new Rules();

    /**
     * 获取规则
     * @return 规则
     */
    @RequestMapping(value="/getRule",method=RequestMethod.GET)
    public CommonResponse getRule(){
        if(global_rules.getId()==0){
            Rules rules= (Rules) ruleServer.getObjectById(1+"");
            if(null!=rules){
                global_rules=rules;
            }else{
                global_rules.setId(1);
                ruleServer.save(global_rules);
            }
            logger.info("从数据库中获取规则成功");
            return new CommonResponse("获取成功","info",global_rules);
        }else{
            logger.info("从缓存中获取规则成功");
            return new CommonResponse("获取成功","info",global_rules);
        }
    }

    /**
     * 修改红包规则
     * @param req 规则参数
     * @return 修改结果
     */
    @RequestMapping(value="/editRedPackage",method = RequestMethod.POST)
    public CommonResponse editRedPackage(@RequestBody RedPackageChangeReq req){
        try {
            global_rules.setLevel1(req.getLevel1());
            global_rules.setLevel2(req.getLevel2());
            global_rules.setLevel3(req.getLevel3());
            global_rules.setLevel4(req.getLevel4());
            ruleServer.save(global_rules);
            logger.info("红包规则修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("红包规则修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改提现代理规则
     * @param req 规则参数
     * @return 修改结果
     */
    @RequestMapping(value="/editProxy",method = RequestMethod.POST)
    public CommonResponse editProxy(@RequestBody ProxyChangeReq req){
        try {
            global_rules.setType(req.getType());
            global_rules.setCountryNum(req.getCountryNum());
            global_rules.setProvinceNum(req.getProvinceNum());
            global_rules.setCityNum(req.getCityNum());
            global_rules.setCountyNum(req.getCountyNum());
            global_rules.setCountryPromotionNum(req.getCountryPromotionNum());
            global_rules.setProvincePromotionNum(req.getProvincePromotionNum());
            global_rules.setCityPromotionNum(req.getCityPromotionNum());
            global_rules.setCountyPromotionNum(req.getCountyPromotionNum());
            global_rules.setSchoolNum(req.getSchoolNum());
            global_rules.setHuawenNum(req.getHuawenNum());
            global_rules.setGuyunNum(req.getGuyunNum());
            global_rules.setXunguNum(req.getXunguNum());
            global_rules.setWithdrawNum(req.getWithdrawNum());
            global_rules.setFee(req.getFee());
            global_rules.setTax(req.getTax());
            ruleServer.save(global_rules);
            logger.info("提现代理规则修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("提现代理修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }
}
