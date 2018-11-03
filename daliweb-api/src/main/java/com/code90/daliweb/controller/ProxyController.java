package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.shop.OrderSearchReq;
import com.code90.daliweb.request.shop.ProxySaveReq;
import com.code90.daliweb.request.shop.ProxySearchReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.*;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理管理控制类
 * @author Ray Lin
 * @create 2018-10-23 23:41
 **/
@RestController
@RequestMapping("/daliweb/proxy")
public class ProxyController {
    private static final Logger logger=LoggerFactory.getLogger(ProxyController.class);
    @Autowired
    private ProxyServer proxyServer;
    @Autowired
    private UserServer userServer;
    @Autowired
    private OrderDetailServer orderDetailServer;
    @Autowired
    private CommodityServer commodityServer;

    /**
     * 新增代理
     * @param req 代理内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addProxy",method = RequestMethod.POST)
    public CommonResponse addProxy(@RequestBody ProxySaveReq req){
        try {
            Proxy proxy=new Proxy();
            BeanUtils.copyProperties(req,proxy);
            proxy.createBy=req.getCreateBy();
            proxyServer.save(proxy);
            User user=userServer.getUserByUserCode(proxy.createBy);
            if(null!=user){
                user.setAgencyLevel(proxy.getType());
                userServer.save(user);
            }
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改代理
     * @param id 编号
     * @param bankName 银行名字
     * @param bankCode 银行号码
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editProxy",method = RequestMethod.GET)
    public CommonResponse editProxy(@RequestParam("id")String id,String bankName,String bankCode,int status){
        try {
            Proxy proxy=(Proxy)proxyServer.getObjectById(id);
            if(!StringUtil.isEmpty(bankName)) {
                proxy.setBankName(bankName);
            }
            if(!StringUtil.isEmpty(bankCode)) {
                proxy.setBankCode(bankCode);
            }
            proxy.setStatus(status);
            proxyServer.save(proxy);
            logger.info("代理修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据编号获取代理
     * @param id 编号
     * @return 代理
     */
    @RequestMapping(value="/getProxyById",method=RequestMethod.GET)
    public CommonResponse getQuestionById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Proxy proxy=(Proxy) proxyServer.getObjectById(id);
        if(proxy!=null){
            ProxyVo proxyVo=new ProxyVo();
            BeanUtils.copyProperties(proxy,proxyVo);
            User user=userServer.getUserByUserCode(proxy.createBy);
            if(null!=user) {
                proxyVo.setUserName(user.getUserName());
                proxyVo.setPhone(user.getPhone());
            }
            logger.info("获取成功");
            response.addNewDate("info",proxyVo);
            return response;
        }else {
            logger.error("获取失败");
            return new CommonResponse("获取失败");
        }
    }

    /**
     * 分页查询代理
     * @param req 分页条件
     * @return 商品列表
     */
    @RequestMapping(value="/getProxys",method=RequestMethod.GET)
    public CommonResponse getProxys(ProxySearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Proxy> allProxy=proxyServer.getAll(req);
        int total=allProxy.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Proxy> proxies=proxyServer.findProxyCriteria(page,pageSize,req);
        List<ProxyVo> proxyVos=new ArrayList<>();
        for (Proxy proxy:proxies){
            ProxyVo proxyVo=new ProxyVo();
            BeanUtils.copyProperties(proxy,proxyVo);
            User user=userServer.getUserByUserCode(proxy.createBy);
            if(null!=user){
                proxyVo.setUserName(user.getUserName());
                proxyVo.setPhone(user.getPhone());
            }
            proxyVos.add(proxyVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",proxyVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 根据用户获取代理详情
     * @param userCode 编号
     * @param type 类型
     * @return 代理
     */
    @RequestMapping(value="/getProxyDetailByUserCode",method=RequestMethod.GET)
    public CommonResponse getQuestionById(@RequestParam("userCode")String userCode,@RequestParam("type")int type){
        try{
            NumberFormat nf=NumberFormat.getNumberInstance() ;
            nf.setMaximumFractionDigits(2);
            CommonResponse response=new CommonResponse("获取成功");
            List<ProxyDetail> proxyDetails=proxyServer.getCurrentMoneyByUserCode(userCode,type);
            List<ProxyDetailVo> proxyDetailVos=new ArrayList<>();
            double currentProxyMoney=0;
            double currentPayMoney=0;
            double currentTotalMoney=0;
            for(ProxyDetail proxyDetail : proxyDetails){
                currentProxyMoney+=proxyDetail.getMoney();
                ProxyDetailVo proxyDetailVo=new ProxyDetailVo();
                BeanUtils.copyProperties(proxyDetail,proxyDetailVo);
                List<OrderDetail> orderDetails=orderDetailServer.getOrderDetailByOrderId(proxyDetail.getOrderNo());
                for(OrderDetail orderDetail : orderDetails){
                    Commodity commodity= (Commodity) commodityServer.getObjectById(orderDetail.getCommodityId());
                    proxyDetailVo.setTotalMoney(Double.parseDouble(nf.format(proxyDetailVo.getTotalMoney()+commodity.getPrice()*orderDetail.getOrderNum())));
                    currentTotalMoney+=(commodity.getPrice()*orderDetail.getOrderNum());
                    proxyDetailVo.setPayMoney(Double.parseDouble(nf.format(proxyDetailVo.getPayMoney()+orderDetail.getMoney())));
                    currentPayMoney+=orderDetail.getMoney();
                }
                proxyDetailVos.add(proxyDetailVo);
            }
            response.addNewDate("info",proxyDetailVos);
            response.addNewDate("currentProxyMoney",nf.format(currentProxyMoney));
            response.addNewDate("currentPayMoney",nf.format(currentPayMoney));
            response.addNewDate("currentTotalMoney",nf.format(currentTotalMoney));
            return response;
        }catch (Exception e){
            logger.error(e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 根据地区编号获取代理列表
     * @param postalCode 地区编号
     * @return 代理
     */
    @RequestMapping(value="/getProxyByPostalCode",method=RequestMethod.GET)
    public CommonResponse getProxyByPostalCode(@RequestParam("postalCode")String postalCode){
        CommonResponse response=new CommonResponse("获取成功");
        List<Proxy> proxies=proxyServer.getProxyByPostalCode(postalCode);
        List<ProxyVo> proxyVos=new ArrayList<>();
        for(Proxy proxy : proxies){
            ProxyVo proxyVo=new ProxyVo();
            BeanUtils.copyProperties(proxy,proxyVo);
            User user=userServer.getUserByUserCode(proxy.createBy);
            proxyVo.setPhone(user.getPhone());
            proxyVo.setUserName(user.getUserName());
            proxyVos.add(proxyVo);
        }
        response.addNewDate("totalCount",proxyVos.size());
        response.addNewDate("info",proxyVos);
        return response;
    }

}
