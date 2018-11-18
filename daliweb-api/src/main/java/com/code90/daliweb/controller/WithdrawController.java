package com.code90.daliweb.controller;

import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.shop.WithdrawSaveReq;
import com.code90.daliweb.request.shop.WithdrawSearchReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.ProxyServer;
import com.code90.daliweb.server.RuleServer;
import com.code90.daliweb.server.UserServer;
import com.code90.daliweb.server.WithdrawServer;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.ExcelUtils;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 提现控制类
 * @author Ray Lin
 * @create 2018-10-28 16:28
 **/
@RestController
@RequestMapping("/daliweb/withdraw")
public class WithdrawController {
    private static final Logger logger=LoggerFactory.getLogger(WithdrawController.class);
    @Autowired
    private WithdrawServer withdrawServer;
    @Autowired
    private RuleServer ruleServer;
    @Autowired
    private ProxyServer proxyServer;
    @Autowired
    private UserServer userServer;

   /**
     * 新增提现
     * @param req 提现内容
     * @return 新增结果
     */
    @RequestMapping(value = "/addWithdraw",method = RequestMethod.POST)
    public CommonResponse addWithdraw(@RequestBody WithdrawSaveReq req){
        try {
            Withdraw withdraw=new Withdraw();
            Rules rules= (Rules) ruleServer.getObjectById(1+"");
            BeanUtils.copyProperties(req,withdraw);
            withdraw.createBy=req.getCreateBy();
            double feeMoney=req.getMoney()*rules.getFee()/100;
            double taxMoney=req.getMoney()*rules.getTax()/100;
            withdraw.setFeeMoney(new BigDecimal(feeMoney).setScale(2, RoundingMode.UP).doubleValue());
            withdraw.setTaxMoney(new BigDecimal(taxMoney).setScale(2, RoundingMode.UP).doubleValue());
            withdraw.setPayMoney(new BigDecimal(req.getMoney()-feeMoney-taxMoney).setScale(2, RoundingMode.UP).doubleValue());
            withdrawServer.save(withdraw);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改提现状态
     * @param  ids 提现编号
     * @param  status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editState",method = RequestMethod.GET)
    public CommonResponse editState(@RequestParam("ids")String ids, @RequestParam("status")int status,String description){
        try {
            if(!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Withdraw withdraw = (Withdraw) withdrawServer.getObjectById(id);
                    withdraw.setStatus(status);
                    withdraw.setDescription(description);
                    withdrawServer.save(withdraw);
                }
                logger.info("提交修改成功");
                return new CommonResponse("修改成功");
            }else{
                logger.error("提交修改失败，编号不能为空");
                return new CommonResponse("修改失败",2);
            }
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 根据提现编号获取提现详情
     * @param id 提现编号
     * @return 提现详情
     */
    @RequestMapping(value="/getWithdrawById",method=RequestMethod.GET)
    public CommonResponse getWithdrawById(@RequestParam("id")String id){
        CommonResponse response=new CommonResponse("获取成功");
        Withdraw withdraw= (Withdraw) withdrawServer.getObjectById(id);
        if(null!=withdraw) {
            logger.info("获取成功");
            response.addNewDate("info", withdraw);
        }
        return response;
    }

    /**
     * 根据用户帐号获取提现详情
     * @param userCode 用户帐号
     * @return 提现详情
     */
    @RequestMapping(value="/getWithdrawByUserCode",method=RequestMethod.GET)
    public CommonResponse getWithdrawByUserCode(@RequestParam("userCode")String userCode){
        CommonResponse response=new CommonResponse("获取成功");
        List<Withdraw> withdraws= withdrawServer.getWithdrawByUserCode(userCode);
        logger.info("获取成功");
        response.addNewDate("info", withdraws);
        return response;
    }

    /**
     * 根据用户帐号获取提现总额
     * @param userCode 用户帐号
     * @return 提现总额
     */
    @RequestMapping(value="/getWithdrawMoneyByUserCode",method=RequestMethod.GET)
    public CommonResponse getWithdrawMoneyByUserCode(@RequestParam("userCode")String userCode){
        try{
            CommonResponse response=new CommonResponse("获取成功");
            Rules rules= (Rules) ruleServer.getObjectById(1+"");
            double proxyMoney=proxyServer.getAllMoneyByUserCode(userCode);
            double withdrawMoney=withdrawServer.getAllMoneyByUserCode(userCode);
            double totalMoney=proxyMoney-withdrawMoney;
            logger.info("获取成功");
            Proxy proxy=proxyServer.getProxyByUserCode(userCode);
            NumberFormat nf=NumberFormat.getNumberInstance() ;
            nf.setMaximumFractionDigits(2);
            response.addNewDate("withdrawNum",rules.getWithdrawNum());
            response.addNewDate("bankName",proxy.getBankName());
            response.addNewDate("bankCode",proxy.getBankCode().substring(proxy.getBankCode().length()-4));
            response.addNewDate("totalMoney", nf.format(totalMoney));
            return response;
        }catch (Exception e){
            logger.error("获取失败,原因："+e.getMessage());
            return new CommonResponse("获取失败",2,e);
        }
    }

    /**
     * 分页查询提成
     * @param req 分页条件
     * @return 提成列表
     */
    @RequestMapping(value="/getWithdraws",method=RequestMethod.GET)
    public CommonResponse getWithdraws(WithdrawSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Withdraw> allWithdraws=withdrawServer.getAll(req);
        int total=allWithdraws.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Withdraw> withdraws=withdrawServer.findWithdrawCriteria(page,pageSize,req);
        List<WithdrawVo> withdrawVos=new ArrayList<>();
        for (Withdraw withdraw : withdraws){
            WithdrawVo withdrawVo=new WithdrawVo();
            BeanUtils.copyProperties(withdraw,withdrawVo);
            User user=userServer.getUserByUserCode(withdraw.createBy);
            if(null!=user) {
                withdrawVo.setUserName(user.getUserName());
            }
            withdrawVos.add(withdrawVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",withdrawVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    @RequestMapping(value = "/exportWithdraw", method = RequestMethod.GET)
    public void exportWithdraw(HttpServletResponse response, @RequestParam("ids") String ids) throws Exception {
        List<ExcelData> excelDatas = new ArrayList<>();
        ExcelData ordersExcel = new ExcelData();
        ordersExcel.setName("提现信息数据");
        List<String> titles = new ArrayList();
        titles.add("申请人姓名");
        titles.add("提现金额");
        titles.add("提现手续费");
        titles.add("提现代扣税");
        titles.add("实际提取金额");
        titles.add("申请时间");
        titles.add("状态");
        titles.add("审核意见");
        titles.add("银行名称");
        titles.add("银行卡号");
        ordersExcel.setTitles(titles);
        //添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        List<WithdrawVo> withdrawVos = new ArrayList<>();
        if (!StringUtil.isEmpty(ids)) {
            String[] id_list = ids.split(",");
            for (String id : id_list) {
                Withdraw withdraw = (Withdraw) withdrawServer.getObjectById(id);
                if (withdraw != null) {
                    WithdrawVo withdrawVo = new WithdrawVo();
                    BeanUtils.copyProperties(withdraw, withdrawVo);
                    User user=userServer.getUserByUserCode(withdraw.createBy);
                    if(null!=user) {
                        withdrawVo.setUserName(user.getUserName());
                    }
                    withdrawVos.add(withdrawVo);
                }
            }
        }
        for (int i = 0; i < withdrawVos.size(); i++) {
            row = new ArrayList();
            row.add(withdrawVos.get(i).getUserName());
            row.add(withdrawVos.get(i).getMoney());
            row.add(withdrawVos.get(i).getFeeMoney());
            row.add(withdrawVos.get(i).getTaxMoney());
            row.add(withdrawVos.get(i).getPayMoney());
            row.add(DateUtils.dateToDateString(withdrawVos.get(i).createTime,DateUtils.ZHCN_DATATIMEF_STR));
            row.add(withdrawVos.get(i).getStatus());
            row.add(withdrawVos.get(i).getDescription());
            Proxy proxy=proxyServer.getProxyByUserCode(withdrawVos.get(i).createBy);
            row.add(proxy.getBankName());
            row.add(proxy.getBankCode());
            rows.add(row);
        }
        ordersExcel.setRows(rows);
        excelDatas.add(ordersExcel);
        String fileName = "提现订单信息" + DateUtils.dateToDateString(new Date(), DateUtils.ZHCN_DATATIMEF_STR) + ".xls";
        ExcelUtils.exportExcel(response, fileName, excelDatas);
    }

    /**
     * 获取提成统计
     * @param req 分页条件
     * @return 提成统计
     */
    @RequestMapping(value="/getWithdrawSummary",method=RequestMethod.GET)
    public CommonResponse getWithdrawSummary(WithdrawSearchReq req){
        CommonResponse response= new CommonResponse("获取成功");
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Withdraw> allWithdraws=withdrawServer.getAll(req);
        double totalMoney=0.0;
        double totalFee=0.0;
        double toatlTax=0.0;
        double totalPayMoney=0.0;
        for(Withdraw withdraw: allWithdraws){
            totalMoney+=withdraw.getMoney();
            totalFee+=withdraw.getFeeMoney();
            toatlTax+=withdraw.getTaxMoney();
            totalPayMoney+=withdraw.getPayMoney();
        }
        int total=allWithdraws.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        NumberFormat nf=NumberFormat.getNumberInstance() ;
        nf.setMaximumFractionDigits(2);
        response.addNewDate("totalMoney",nf.format(totalMoney));
        response.addNewDate("totalFee",nf.format(totalFee));
        response.addNewDate("toatlTax",nf.format(toatlTax));
        response.addNewDate("totalPayMoney",nf.format(totalPayMoney));
        List<Withdraw> withdraws=withdrawServer.findWithdrawCriteria(page,pageSize,req);
        List<WithdrawVo> withdrawVos=new ArrayList<>();
        for (Withdraw withdraw : withdraws){
            WithdrawVo withdrawVo=new WithdrawVo();
            BeanUtils.copyProperties(withdraw,withdrawVo);
            User user=userServer.getUserByUserCode(withdraw.createBy);
            if(null!=user) {
                withdrawVo.setUserName(user.getUserName());
            }
            withdrawVos.add(withdrawVo);
        }
        response.addNewDate("info",withdrawVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        return response;
    }

    @RequestMapping(value = "/exportWithdrawSummary", method = RequestMethod.GET)
    public void exportWithdraw(HttpServletResponse response, WithdrawSearchReq req) throws Exception {
        List<ExcelData> excelDatas = new ArrayList<>();
        ExcelData ordersExcel = new ExcelData();
        ordersExcel.setName("提现统计数据");
        List<String> titles = new ArrayList();
        titles.add("申请人姓名");
        titles.add("提现金额");
        titles.add("提现手续费");
        titles.add("提现代扣税");
        titles.add("实际提取金额");
        titles.add("申请时间");
        ordersExcel.setTitles(titles);
        //添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        List<WithdrawVo> withdrawVos = new ArrayList<>();
        List<Withdraw> allWithdraws=withdrawServer.getAll(req);
        double totalMoney=0.0;
        double totalFee=0.0;
        double toatlTax=0.0;
        double totalPayMoney=0.0;
        for(Withdraw withdraw:allWithdraws){
            WithdrawVo withdrawVo = new WithdrawVo();
            BeanUtils.copyProperties(withdraw, withdrawVo);
            User user=userServer.getUserByUserCode(withdraw.createBy);
            if(null!=user) {
                withdrawVo.setUserName(user.getUserName());
            }
            withdrawVos.add(withdrawVo);
            totalMoney+=withdraw.getMoney();
            totalFee+=withdraw.getFeeMoney();
            toatlTax+=withdraw.getTaxMoney();
            totalPayMoney+=withdraw.getPayMoney();
        }
        for (int i = 0; i < withdrawVos.size(); i++) {
            row = new ArrayList();
            row.add(withdrawVos.get(i).getUserName());
            row.add(withdrawVos.get(i).getMoney());
            row.add(withdrawVos.get(i).getFeeMoney());
            row.add(withdrawVos.get(i).getTaxMoney());
            row.add(withdrawVos.get(i).getPayMoney());
            row.add(DateUtils.dateToDateString(withdrawVos.get(i).createTime,DateUtils.ZHCN_DATATIMEF_STR));
            rows.add(row);
        }
        row=new ArrayList<>();
        row.add("总计：");
        NumberFormat nf=NumberFormat.getNumberInstance() ;
        nf.setMaximumFractionDigits(2);
        row.add(nf.format(totalMoney));
        row.add(nf.format(totalFee));
        row.add(nf.format(toatlTax));
        row.add(nf.format(totalPayMoney));
        rows.add(row);
        ordersExcel.setRows(rows);
        excelDatas.add(ordersExcel);
        String fileName = "提现统计数据" + DateUtils.dateToDateString(new Date(), DateUtils.ZHCN_DATATIMEF_STR) + ".xls";
        ExcelUtils.exportExcel(response, fileName, excelDatas);
    }
}

