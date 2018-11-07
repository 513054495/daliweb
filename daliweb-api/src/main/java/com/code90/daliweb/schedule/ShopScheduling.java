package com.code90.daliweb.schedule;

import com.code90.daliweb.conf.RedisServer;
import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.shop.OrderSearchReq;
import com.code90.daliweb.server.*;
import com.code90.daliweb.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商城定时器
 * @author Ray
 * @create 2018-06-11 22:13
 **/

@Configuration
@EnableScheduling
public class ShopScheduling {
    private final Logger logger = LoggerFactory.getLogger(ShopScheduling.class);

    @Autowired
    private OrdersServer ordersServer;
    @Autowired
    private OrderDetailServer orderDetailServer;
    @Autowired
    private ProxyServer proxyServer;
    @Autowired
    private CommodityServer commodityServer;
    @Autowired
    private RuleServer ruleServer;
    @Autowired
    private RedPackageServer redPackageServer;
    @Autowired
    private RedisServer redisServer;
    @Autowired
    private UserServer userServer;

    @Scheduled(cron="0 0 2 * * ? ")
    public void orderConfirm(){
        logger.info("订单定时器---->>开始执行");
        OrderSearchReq req=new OrderSearchReq();
        req.setStatus(-1);
        List<Orders> allOrders=ordersServer.getAll(req);
        for (Orders orders : allOrders){
            if(orders.getStatus()==2&&DateUtils.compareTime(DateUtils.daysAfter(orders.modifyTime,15),new Date())){
                logger.error("订单定时器---->>自动确认收货，收货订单号："+orders.getId());
                orders.setStatus(3);
                ordersServer.save(orders);
            }
            List<OrderDetail> orderDetails=orderDetailServer.getOrderDetailByOrderId(orders.getId());
            for (OrderDetail orderDetail : orderDetails){
                if(orderDetail.getStatus()==1&&DateUtils.compareTime(DateUtils.daysAfter(orderDetail.modifyTime,7),new Date())){
                    logger.error("订单定时器---->>自动拒绝退款，拒绝退款订单号："+orders.getId()+",拒绝退款商品号："+orderDetail.getCommodityId());
                    orderDetail.setStatus(3);
                    orderDetailServer.save(orderDetail);
                }
            }
            if(orders.getStatus()==3&&DateUtils.compareTime(DateUtils.daysAfter(orders.modifyTime,7),new Date())){
                logger.error("订单定时器---->>交易关闭，收货订单号："+orders.getId());
                orders.setStatus(5);
                ordersServer.save(orders);
                List<ProxyDetail> proxyDetails=proxyServer.getProxyDetailByOrderId(orders.getId());
                Rules rules= (Rules) ruleServer.getObjectById(1+"");
                double money =0;
                List<OrderDetail> orderDetails1=orderDetailServer.getOrderDetailByOrderId(orders.getId());
                for(OrderDetail orderDetail : orderDetails1){
                    if(orderDetail.getStatus()!=2){
                        Commodity commodity = (Commodity) commodityServer.getObjectById(orderDetail.getCommodityId());
                        if(commodity.getIsVip()==1) {
                            User user = userServer.getUserByUserCode(orders.createBy);
                            user.setUserType(1);
                            userServer.save(user);
                        }
                        if(rules.getType()==0) {
                            money += orderDetail.getOrderNum() * commodity.getPrice();
                        }else{
                            money += orderDetail.getMoney();
                        }
                    }
                }
                for (ProxyDetail proxyDetail : proxyDetails){
                    Proxy proxy=proxyServer.getProxyByUserCode(proxyDetail.createBy);
                    proxyDetail.setStatus(1);
                    proxyDetail.setMoney(calcProxyMoney(proxy.getType(),money,rules,proxyDetail.getType()));
                    proxyServer.saveProxyDetail(proxyDetail);
                }
            }
        }
        logger.info("订单定时器---->>结束执行");
    }

    @Scheduled(cron="0 0/1 * * * ? ")
    public void orderCancel(){
        logger.info("订单取消定时器---->>开始执行");
        OrderSearchReq req=new OrderSearchReq();
        req.setStatus(-1);
        List<Orders> allOrders=ordersServer.getAll(req);
        for (Orders orders : allOrders){
            if(orders.getStatus()==0&&DateUtils.compareTime(DateUtils.minuteAfter(orders.modifyTime,30),new Date())){
                logger.error("订单取消定时器---->>订单号："+orders.getId()+"被删除");
                orders.setStatus(6);
                ordersServer.save(orders);
                if(orders.getDeductionMonry()!=0){
                    RedPackageDetail redPackageDetail=new RedPackageDetail();
                    redPackageDetail.setCreateBy(orders.createBy);
                    redPackageDetail.setType(3);
                    redPackageDetail.setMoney(orders.getDeductionMonry());
                    redPackageDetail.setOrderNo(orders.getId());
                    redPackageServer.save(redPackageDetail);
                }
                List<OrderDetail> orderDetails=orderDetailServer.getOrderDetailByOrderId(orders.getId());
                for (OrderDetail orderDetail : orderDetails){
                    int num=redisServer.getNum(orderDetail.getCommodityId());
                    redisServer.setValue(orderDetail.getCommodityId(),(num+orderDetail.getOrderNum())+"");
                }
            }
        }
        logger.info("订单取消定时器---->>结束执行");
    }

    private double calcProxyMoney(int type, double orderMoney,Rules rules,int proxyType) {
        double money=0;
        switch(type){
            case 1:
                if(proxyType==0) {
                    money = orderMoney * rules.getCountryPromotionNum() / 100;
                }else{
                    money = orderMoney * rules.getCountryNum()/100;
                }
                break;
            case 2:
                if(proxyType==0) {
                    money=orderMoney*rules.getProvincePromotionNum()/100;
                }else{
                    money = orderMoney * rules.getProvinceNum()/100;
                }
                break;
            case 3:
                if(proxyType==0) {
                    money = orderMoney * rules.getCityPromotionNum() / 100;
                }else{
                    money = orderMoney*rules.getCityNum()/100;
                }
                break;
            case 4:
                if(proxyType==0) {
                    money = orderMoney * rules.getCountyPromotionNum() / 100;
                }else{
                    money=orderMoney*rules.getCountyNum()/100;
                }
                break;
            case 5:
                money=orderMoney*rules.getSchoolNum()/100;
                break;
            case 6:
                money=orderMoney*rules.getHuawenNum()/100;
                break;
            case 7:
                money=orderMoney*rules.getGuyunNum()/100;
                break;
            case 8:
                money=orderMoney*rules.getXunguNum()/100;
                break;
        }
        return money;
    }

}
