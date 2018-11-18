package com.code90.daliweb.controller;

import com.code90.daliweb.domain.LearnTopic;
import com.code90.daliweb.domain.LevelApplication;
import com.code90.daliweb.domain.Orders;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.text.NumberFormat;
import java.util.List;

/**
 * 首页控制类
 * @author Ray Lin
 * @create 2018-11-13 23:09
 **/
@RestController
@RequestMapping("daliweb/home")
public class HomeController {
    private static final Logger logger=LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private OrdersServer ordersServer;
    @Autowired
    private OrderDetailServer orderDetailServer;
    @Autowired
    private WithdrawServer withdrawServer;
    @Autowired
    private LeaveMessageServer leaveMessageServer;
    @Autowired
    private QuestionServer questionServer;
    @Autowired
    private CompetitionDetailServer competitionDetailServer;
    @Autowired
    private LevelApplicationServer levelApplicationServer;
    @Autowired
    private UserServer userServer;
    @Autowired
    private RedPackageServer redPackageServer;
    @Autowired
    private ProxyServer proxyServer;

    @RequestMapping(value = "/getToDoList",method = RequestMethod.GET)
    public CommonResponse getToDoList(){
        CommonResponse response=new CommonResponse("获取成功");
        int shipTodoNum=ordersServer.getOrdersByStatus(1);
        response.addNewDate("shipTodoNum",shipTodoNum);
        int refundTodoNum=orderDetailServer.getOrderDetailByStatus(1);
        response.addNewDate("refundTodeNum",refundTodoNum);
        int withdrawTodoNum=withdrawServer.getWithdrawByStatus(0);
        response.addNewDate("withdrawTodoNum",withdrawTodoNum);
        int leaveMessageTodeNum=leaveMessageServer.getLeaveMessageByState(0);
        response.addNewDate("leaveMessageTodeNum",leaveMessageTodeNum);
        int questionTodeNum=questionServer.getQuestionByStatus(0);
        response.addNewDate("questionTodeNum",questionTodeNum);
        int competitionDetailTodoNum=competitionDetailServer.getDetailByStatus(0);
        response.addNewDate("competitionDetailTodoNum",competitionDetailTodoNum);
        int levelApplicationTodoNum=levelApplicationServer.getLevelApplicationByStatus(0);
        response.addNewDate("levelApplicationTodoNum",levelApplicationTodoNum);
        return response;
    }

    @RequestMapping(value = "/getComprehensive",method = RequestMethod.GET)
    public CommonResponse getComprehensive(){
        CommonResponse response=new CommonResponse("获取成功");
        int newUserNum=userServer.getNewUserByCurrentMonth();
        response.addNewDate("newUserNum",newUserNum);
        double redPackageNum=redPackageServer.getAllRedPackage();
        response.addNewDate("redPackageNum",redPackageNum);
        double proxyMoney=proxyServer.getAllMoney();
        double withdrawMoney=withdrawServer.getAllMoney();
        double totalMoney=proxyMoney-withdrawMoney;
        NumberFormat nf=NumberFormat.getNumberInstance() ;
        nf.setMaximumFractionDigits(2);
        response.addNewDate("totalMoney",nf.format(totalMoney));
        int newVipUserNum=userServer.getNewVipUserByCurrentMonth();
        response.addNewDate("newVipUserNum",newVipUserNum);
        return response;
    }

    @RequestMapping(value = "/getLiquidity",method = RequestMethod.GET)
    public CommonResponse getLiquidity(){
        CommonResponse response=new CommonResponse("获取成功");
        List<Object[]> list=ordersServer.getSalesByYear();
        if(null!=list){
            String[] salesMonths=new String[list.size()];
            String[] sales=new String[list.size()];
            for(int i=0;i<list.size();i++){
                salesMonths[i]=list.get(i)[0].toString();
                sales[i]=list.get(i)[1].toString();
            }
            response.addNewDate("salesMonths",salesMonths);
            response.addNewDate("sales",sales);
        }
        List<Object[]> list1=proxyServer.getProxyDetailByYear();
        if(null!=list1){
            String[] proxyDetailMonths=new String[list1.size()];
            String[] proxyDetails=new String[list1.size()];
            for(int i=0;i<list1.size();i++){
                proxyDetailMonths[i]=list1.get(i)[0].toString();
                proxyDetails[i]=list1.get(i)[1].toString();
            }
            response.addNewDate("proxyDetailMonths",proxyDetailMonths);
            response.addNewDate("proxyDetails",proxyDetails);
        }
        List<Object[]> list2=withdrawServer.getWithdrawByYear(0);
        if(null!=list2){
            String[] withdrawMonths=new String[list2.size()];
            String[] withdrawDetails=new String[list2.size()];
            for(int i=0;i<list2.size();i++){
                withdrawMonths[i]=list2.get(i)[0].toString();
                withdrawDetails[i]=list2.get(i)[1].toString();
            }
            response.addNewDate("withdrawMonths",withdrawMonths);
            response.addNewDate("withdrawDetails",withdrawDetails);
        }
        List<Object[]> list3=withdrawServer.getWithdrawByYear(1);
        if(null!=list3){
            String[] successWithdrawMonths=new String[list3.size()];
            String[] successWithdrawDetails=new String[list3.size()];
            for(int i=0;i<list3.size();i++){
                successWithdrawMonths[i]=list3.get(i)[0].toString();
                successWithdrawDetails[i]=list3.get(i)[1].toString();
            }
            response.addNewDate("successWithdrawMonths",successWithdrawMonths);
            response.addNewDate("successWithdrawDetails",successWithdrawDetails);
        }
        return response;
    }
}
