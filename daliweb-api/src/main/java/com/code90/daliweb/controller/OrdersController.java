package com.code90.daliweb.controller;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.code90.daliweb.conf.MyWxPayUtil;
import com.code90.daliweb.conf.WXPayClient;
import com.code90.daliweb.server.*;
import com.code90.daliweb.utils.HttpUtil;
import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.shop.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.utils.*;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 订单控制类
 * @author Ray Lin
 * @create 2018-09-15 1:27
 **/
@RestController
@RequestMapping("/daliweb/order")
public class OrdersController {
    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrdersServer orderServer;
    @Autowired
    private OrderDetailServer orderDetailServer;
    @Autowired
    private CommodityServer commodityServer;
    @Autowired
    private RedPackageServer redPackageServer;
    @Autowired
    private ProxyServer proxyServer;
    @Autowired
    private UserServer userServer;
    @Autowired
    private ShoppingCartServer shoppingCartServer;
    @Autowired
    private WXPay wxPay;
    @Autowired
    private WXPayClient wxPayClient;
    @Value("${alipay.server_url}")
    private String server_url;
    private String private_key_test = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDRlXStthj2ezXevaNPgO52gtPu9T/aiYkAZZMwmbGFu9qV1WP0w6QNjQ398Z2bDFY2vG6Uy9zeS/xSqYUiPTW9Feh9cKu2wcnutfoZi8UIiyfHg8CE68TDc1NyOCGz0GRxxDvuXsBWolKm2U1Rh9zQ7XbUinOdl9vU1LUZSzvesI8xJD1hqL4p3AwF01n3dgRndPCa/3l8fGz2RslYyfwZO5et3lyaX8tqXwOFNAof1FJonrcb+i81SJIW1OVrEg0lqx+6edKJgPc7vi2pM2+L7y1nTC3EnFikDphOqEliy232djVQBFkcWIvMwiMMF5ZCpw5oP/XbIANGCWhhqPbPAgMBAAECggEBAI5u7vPbbI2eYJb29FTkv6sk8FlWrUWc8NGnNmfyG7ZSW5Q2iI7gos5ezWKG4OBteu4KE3DmNz2QEHQxJvyE8s+FYxJVDy3N96lWXRkdZxOOVsN948mx3XAjIVPfl+/3MTwr67rtA/QcruUbizNcjRIkP/zj9vzkJo8227jL621D/4/GACoQGJsdy9GWp88ckcsJ0MKNxUFl9j6qkItJORg9jVTABbJsqmpO/Ivw+mcDr0sV3rL3dtjYgZnevXUfZkOb+OuuPCLsWKzubABBThQG14fFBiMb/ckhNkp73ObAcFAPnd2ZoXvBnLCGs4nWppEt8wQMGnvyg9pXVEJk2uECgYEA8EJEKUs2Tnet2NaYICiLXiV1rgCV5flEEfFTKGd5tl2fvk1BxkBDrDowq+8CW+sQue3iu8LtK+7AWzBTkcwuZBXHGtMMUyPeJzgTT9SNaTTLasLd5n9KY/VWTKz+M4Mr2SG64Jj8O/6dC2qV8z1FTko0lpvemxKwDUlk8zh4ZrECgYEA31Cxgqg+Vay0zrAw/hQp4B90i2to6pQvshmn/5A00CddQiPpxYV5hidoWXd1LLKGWHk7stRDzq/3mRNwdfbh/E6XfvuhV/Xs3oC7jvYq85nKkx85FVPfbSHRrNhmSFLtbccarOVidmTgtzvSO9czWOAr3Hh5QDR485i9Rej5lX8CgYEAlQ5XoVLTL11aksOX/6OHy3HsD3a3iMTbsVIJ6HBB950gJP1jSC7IjUpQteqDiSlN6DwoxZpKcE4gMrLJthN52IgWTZMP+2iuQ6CrgoR48wNZDDQ3XPAt49PqpYlR7/16L6yYdw0St9nf7pxCyXSC0e6Br8qmEYlHhga5xZeMQxECgYEAsbroPCOo0MkOcb16kIz9svl16QxISQI9X5LZySU9UzkxGNW6VVDgoiu0+hd/8132XG3IXJBhkxsrhPIavFgMDaGDIzYjd/0rUv7Dx4kWMCCDQEpGRTomhRW4acckyITvoEIpcddyYOXyjwKWVLf2jtKDNgnP/pCEfuNeKEi+cwMCgYEAraHcnKf5nDT3XoWmrp6oV2oSGqdC1NvP4XKpwKsQ7n5+Nd7+jgGEDY8vjkwZ3RJkPmPtgsSXpVUiXfJxHTxccC41tQK2VIYEfNoUbD1lGZaZKI13MPO+NhnoTRafRrchkDAsrl8+ooKVqOUJ9EDIGxcwHUiNL8BikWEd3yZSUtA=";
    private String private_key_prod = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC7wTGCqJNu+u35Xq9nfxS0UAzB+1oz4qbbHQBV3eR0Fiz3e3VdwYXR558F26z/DxrsI3aenw8/jp45paqaVlTo0hYGw82iHAiWIxb+1OJwPT7CVyacxqMIpa+N65mCTccpbCE+Fesi+lmtzzFTWbfoGcjHu1LLe65Q2xMFKi4PAaEds4MoiXOFL/4TzEsvPAJ8t4YzUZdEl6I9DonAs7OOQUUtW4YOGuNHurhrcTm2q2aHLoXg3G2d7x+iITGy/DHNCFbbMsc6bRXwDyszn6kfSC/Wo474i/Kb6cdVgcjUx8H1ZddIIA2B5/MYiUvZijelLbRLBWHxJzjEjGdKOPMvAgMBAAECggEAVLeIYn8z/xiENkft91IVAoM4LnL+sXisdyOv2g7EHUlAQu7W91XxztOnIlqLxY6IRFKAHHSCydfNNw+0We1oZKEexTCmYWYqzavJnXRrM9++qKvwRT8voyA3hV7wJiZ4Hg2nNj9nmKHDwzRA+m36GGVB/jVc12nYLq2a+HKe06/Pp1ZSrcMumJJNjB1viP3jjlaj+dOShv37BQlFzKaQHdbCxCtXWQyoVq3jHfHPjVvcd7nrlnABSyxwNdLOBvo5HWDgWY3FoMmaO1P5qk4WKnrIsMNFBLhGj/TTO8//lcnfIXzhD0LH43aQdwc7Q52emZFnMpK5e2lB7NiKTioseQKBgQDehDIVXD94ehLJBU6FQcC0Bq9dX49Axgy/K3/ny/LKbSHD364PANQo8EALDV8dONGKM4inbMYkNKkkNLwj4IfiIGrnRCkzCg8I9ESFIc8bSlCiJHeais/TnKW8hUHRHamSzcwft/Ufcj7WWjrr+F9gwP2PWecS8DMpfWzNF37VdQKBgQDYAebxOJ3qxSgdPLqrodujZyGZneyiL/dM1SUqC/x01+S6AhvLsql8LjwUSyWaVIbmn6d3wm3QC5076XotvijslF3O75cT/UMpWEzGKvUFvt2kKBCZpDP1lFReMYT2JnQL20DNty2YU9cZfeiL/SyWF1Nhf6rZFXJxNNXnIvm9kwKBgEnz24VX3ExZHecrWVZWbQon8p5wxPZ9G/1pIqBSGwYF0SP9MicQWnKkrlQ+BG98078/s1oZf5zG17QNxr2RwPd5WK417A9nd2m9bQxmGSPzxeqtfgahvcKRnE+UZjRuzE3bEc004UWrPZEV+eE08dYBLgQ3l+GmbQqMAd4DwgaFAoGAQPkpf0t5bMHLFChNOg0gpOEDoVJ4eDyXVXwThrc+Q+MgwLabp8Tg6CJ8HdqQwABG554EYKiSIW8s53WOM64sXyUtpm448mFOz2PpC/6l3wmPX65Rd/WFT4xDaGXaybO9WkekEXzU8Sne3KMbzOflfmwbv4sIV+30k4rCAJlrp5MCgYAy+e/ARzKIeByF0W5Q8fiH8aFsf+F9vZllszJiXg/H5LQObTt85sH7K3fdKG/t9J0ZK5Af2fPnDhxDwCe0/p5jfn/a+bHb7G5BF0ACWsRFi9lK/TjiUX3imEv+nxCXtElMeIoM0Zedsx2BWJGlI5U7tVSSuj2NVo8D9BAQQoC3uw==";
    private String format = "json";
    private String charset = "utf-8";
    private String alipay_public_key_test = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwmbVZabs+xbCU+90lY6c0+etF8XsTjjA0XGv4cYWL5P8ROTy3PrquT73qBDDlScUCkboFYOfu6lacJ5LgcuRgEhz2mIMDVpeLncbE87JrIEtt8lI9YHFqwUFBV44gptB9AMcayZ8mhIAKMJwhggUAIkxF8Uye+9NVdMzCPe/ko2Ifm+KpRp++fGntx7YN7Rc5SmlwatXnEciG4KsMez0mB3dzaIH47mVUJdueXm81aFVJEV5JIAgDVM/no2+HGyPNh1Vj5fbRTZa3gJ34PcCqtQO5dGDlMfiPcYfOd7fG5ihtkDOi/UlAlAOvCAeLDVzv6GIk3LhGAcCDgQPdoLHKwIDAQAB";
    private String alipay_public_key_prod = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlrszrBq1hWGxWeKCR6PnRXVyFJ0NjvEkHpzLVXpeRJ4p4U/xSGDlfE9XG2EWQS7aEyEBPpom+HP6+uwGLHZaHCglBOica8Rm3t2GIeSyxBKPjU/ucgLcq4jLJq3GSmM46ZpYmmNUN5UiGDXgHtGaeAETBug5DJeKNg6MG5bEFoRLBrwQpRaF9IY2dzqIETpYZ4Rn1aJx74S9B94M7UIo2SdJZ2MkCPj8k62KlIRM41SndiSzOLwTPqdDDiNz+023AUhAvdzzQmwcdbdayGvwtLqRKPrzuiIpaDiwL17tJx5MymuVmcEywvDXJhiFeOyzpBhNzgtrYjeo2Jj+qZwbJQIDAQAB";
    @Value("${alipay.sign_type}")
    private String sign_type;
    @Value("${alipay.app_id}")
    private String app_id;
    @Value("${alipay.notify_url}")
    private String notify_url;
    @Value("${spring.profiles.active}")
    private String payDev;

    /**
     * 订单生成
     *
     * @param req 订单信息
     * @return 生成结果
     */
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public CommonResponse addOrder(@RequestBody OrdersSaveReq req) {
        try {
            Orders order = new Orders();
            BeanUtils.copyProperties(req, order);
            String id = IdUtils.createOrderId();
            while (orderServer.getObjectById(id) != null) {
                id = IdUtils.createOrderId();
            }
            order.setId(id);
            order.createBy = req.getCreateBy();
            orderServer.save(order);
            List<OrderDetailSaveReq> orderDetailSaveReqs = req.getOrderDetailSaveReqs();
            for (OrderDetailSaveReq orderDetailSaveReq : orderDetailSaveReqs) {
                OrderDetail orderDetail = new OrderDetail();
                BeanUtils.copyProperties(orderDetailSaveReq, orderDetail);
                orderDetail.setOrderId(order.getId());
                orderDetailServer.save(orderDetail);
                Commodity commodity=commodityServer.getCommodityAndDeleteById(orderDetail.getCommodityId());
                commodity.setTotalNum(commodity.getTotalNum()-orderDetail.getOrderNum());
                commodityServer.save(commodity);
                if(req.getIsShoppingCart()!=0) {
                    ShoppingCart shoppingCart = shoppingCartServer.getShoppingCartByCommodityIdAndcreateBy(orderDetail.getCommodityId(), order.createBy);
                    int count = shoppingCart.getNum() - orderDetail.getOrderNum();
                    if (count > 0) {
                        shoppingCart.setNum(count);
                        shoppingCartServer.save(shoppingCart);
                    } else {
                        shoppingCartServer.delete(shoppingCart);
                    }
                }
            }
            logger.info("保存成功");
            return new CommonResponse("保存成功", "orderId", id);
        } catch (Exception e) {
            logger.error("保存失败，原因：" + e.getMessage());
            return new CommonResponse("保存失败", 1, e);
        }
    }

    /**
     * 修改订单
     * @param req 修改订单
     * @return 修改结果
     */
    @RequestMapping(value = "/editOrder", method = RequestMethod.POST)
    public CommonResponse updateOrder(@RequestBody OrdersChangeReq req) {
        try {
            Orders orders = (Orders) orderServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req, orders);
            orderServer.save(orders);
            /*orderDetailServer.delOrderDetailByOrderId(orders.getId());
            List<OrderDetailSaveReq> ordersSaveReqs=req.getOrderDetailSaveReqs();
            for (OrderDetailSaveReq orderDetailSaveReq : ordersSaveReqs){
                OrderDetail orderDetail=new OrderDetail();
                BeanUtils.copyProperties(orderDetailSaveReq,orderDetail);
                orderDetail.setOrderId(orders.getId());
                orderDetailServer.save(orderDetail);
            }*/
            logger.info("订单修改成功");
            return new CommonResponse("修改成功");
        } catch (Exception e) {
            logger.error("修改失败，原因：" + e.getMessage());
            return new CommonResponse("修改失败", 2, e);
        }
    }

    /**
     * 订单状态变更
     *
     * @param ids    订单编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public CommonResponse updateStatus(@RequestParam("ids") String ids, @RequestParam("status") int status, String shipNo) {
        try {
            if (!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Orders orders = (Orders) orderServer.getObjectById(id);
                    orders.setStatus(status);
                    if (!StringUtil.isEmpty(shipNo)) {
                        orders.setShipNo(shipNo);
                    }
                    if(status==2){
                        calcProxyDetail(orders);
                    }
                    if(status==6){
                        if(orders.getDeductionMonry()!=0) {
                            RedPackageDetail redPackageDetail = new RedPackageDetail();
                            redPackageDetail.setCreateBy(orders.createBy);
                            redPackageDetail.setType(3);
                            redPackageDetail.setMoney(orders.getDeductionMonry());
                            redPackageDetail.setOrderNo(orders.getId());
                            redPackageServer.save(redPackageDetail);
                        }
                        List<OrderDetail> orderDetails=orderDetailServer.getOrderDetailByOrderId(orders.getId());
                        for (OrderDetail orderDetail : orderDetails){
                            Commodity commodity= (Commodity) commodityServer.getObjectById(orderDetail.getCommodityId());
                            commodity.setTotalNum(commodity.getTotalNum()+orderDetail.getOrderNum());
                            commodityServer.save(commodity);
                        }
                    }
                    orderServer.save(orders);
                }
            } else {
                logger.error("修改失败，订单编号不能为空");
                return new CommonResponse("修改失败，订单编号不能为空", 2);
            }
            logger.info("订单修改成功");
            return new CommonResponse("修改成功");
        } catch (Exception e) {
            logger.error("修改失败，原因：" + e.getMessage());
            return new CommonResponse("修改失败", 2, e);
        }
    }

    /**
     * 订单详情状态变更
     * @param id     订单编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value = "/editDetailStatus", method = RequestMethod.GET)
    public CommonResponse editDetailStatus(@RequestParam("id") String id, @RequestParam("status") int status, String refundReason) {
        try {
            OrderDetail orderDetail =(OrderDetail) orderDetailServer.getObjectById(id);
            if (null != orderDetail) {
                orderDetail.setStatus(status);
                if (!StringUtil.isEmpty(refundReason)) {
                    orderDetail.setRefundReason(refundReason);
                }
                orderDetailServer.save(orderDetail);
                List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByOrderId(orderDetail.getOrderId());
                int num = 0;
                for (OrderDetail orderDetail1 : orderDetails) {
                    if (orderDetail1.getStatus() == 2) {
                        num++;
                    }
                }
                if (num == orderDetails.size()) {
                    Orders orders = (Orders) orderServer.getObjectById(orderDetail.getOrderId());
                    orders.setStatus(4);
                    proxyServer.deleteByOrdersId(orders.getId());
                    if(orders.getDeductionMonry()!=0) {
                        RedPackageDetail redPackageDetail = new RedPackageDetail();
                        redPackageDetail.setMoney(orders.getDeductionMonry());
                        redPackageDetail.setOrderNo(orders.getId());
                        redPackageDetail.setType(3);
                        redPackageDetail.setCreateBy(orders.createBy);
                        redPackageServer.save(redPackageDetail);
                    }
                    orderServer.save(orders);
                }
                logger.info("订单详情修改成功");
                return new CommonResponse("修改成功");
            } else {
                logger.error("修改失败，订单详情不存在");
                return new CommonResponse("修改失败，订单详情不存在", 2);
            }
        } catch (Exception e) {
            logger.error("修改失败，原因：" + e.getMessage());
            return new CommonResponse("修改失败", 2, e);
        }
    }

    /**
     * 删除订单
     * @param ids 订单编号
     * @return 删除结果
     */
    @RequestMapping(value = "/delOrder", method = RequestMethod.DELETE)
    public CommonResponse delOrder(@RequestParam("ids") String ids) {
        try {
            if (!StringUtil.isEmpty(ids)) {
                String[] id_list = ids.split(",");
                for (String id : id_list) {
                    Orders orders = (Orders) orderServer.getObjectById(id);
                    orderDetailServer.delOrderDetailByOrderId(id);
                    orderServer.delete(orders);
                }
                logger.info("订单删除成功");
                return new CommonResponse("删除成功");
            } else {
                logger.error("删除失败，订单编号不能为空");
                return new CommonResponse("删除失败，订单编号不能为空", 3);
            }
        } catch (Exception e) {
            logger.error("删除失败，原因：" + e.getMessage());
            return new CommonResponse("删除失败", 3, e);
        }
    }

    /**
     * 根据订单编号获取订单
     * @param ids 订单编号
     * @return 订单信息
     */
    @RequestMapping(value = "/getOrderById", method = RequestMethod.GET)
    public CommonResponse getOrderById(@RequestParam("ids") String ids) {
        List<OrdersVo> ordersVos = new ArrayList<>();
        if (!StringUtil.isEmpty(ids)) {
            String[] id_list = ids.split(",");
            for (String id : id_list) {
                Orders orders = (Orders) orderServer.getObjectById(id);
                if (orders != null) {
                    OrdersVo ordersVo = new OrdersVo();
                    BeanUtils.copyProperties(orders, ordersVo);
                    ordersVo.createBy = orders.createBy;
                    ordersVo.createTime = orders.createTime;
                    ordersVo.modifyTime = orders.modifyTime;
                    List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByOrderId(orders.getId());
                    for (OrderDetail orderDetail : orderDetails) {
                        Commodity commodity = commodityServer.getCommodityAndDeleteById(orderDetail.getCommodityId());
                        CommodityVo commodityVo = new CommodityVo();
                        BeanUtils.copyProperties(commodity, commodityVo);
                        commodityVo.setOrderNum(orderDetail.getOrderNum());
                        commodityVo.setStatus(orderDetail.getStatus());
                        commodityVo.setDetailId(orderDetail.getId());
                        commodityVo.setRefundReason(orderDetail.getRefundReason());
                        ordersVo.getOrderCommodities().add(commodityVo);
                    }
                    ordersVos.add(ordersVo);
                }
            }
            logger.error("获取订单成功");
            return new CommonResponse("获取订单成功", "info", ordersVos);
        } else {
            logger.error("获取失败，订单不存在");
            return new CommonResponse("获取失败，订单不存在", 5);
        }
    }

    /**
     * 根据商品编号获取销售量
     * @param ids 商品编号
     * @return 销售量
     */
    @RequestMapping(value = "/getNumByCommodityId", method = RequestMethod.GET)
    public CommonResponse getNumByCommodityId(@RequestParam("ids") String ids) {
        int totalNum = 0;
        if (!StringUtil.isEmpty(ids)) {
            String[] id_list = ids.split(",");
            for (String id : id_list) {
                List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByCommodityId(id);
                for (OrderDetail orderDetail : orderDetails) {
                    totalNum += orderDetail.getOrderNum();
                }
            }
            logger.error("获取销售量成功");
            return new CommonResponse("获取销售量成功", "info", totalNum);
        } else {
            logger.error("获取失败，订单中商品编号不存在");
            return new CommonResponse("获取失败，订单中商品编号不存在", 5);
        }
    }

    /**
     * 根据用户帐号获取各种订单的数量
     * @param userCode
     * @return 数量信息
     */
    @RequestMapping(value = "/getOrderNumByUserCode", method = RequestMethod.GET)
    public CommonResponse getOrderNumByUserCode(@RequestParam("userCode") String userCode) {
        try {
            List<Orders> allOrders = orderServer.getOrdersByCreateBy(userCode);
            int all = allOrders.size();
            int dfkNum = 0;
            int dfhNum = 0;
            int dshNum = 0;
            int tkNum = 0;
            for (Orders orders : allOrders) {
                if (orders.getStatus() == 0) {
                    dfkNum++;
                } else if (orders.getStatus() == 1) {
                    dfhNum++;
                } else if (orders.getStatus() == 2) {
                    dshNum++;
                }
                List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByOrderId(orders.getId());
                for (OrderDetail orderDetail : orderDetails) {
                    if (orderDetail.getStatus() != 0) {
                        tkNum++;
                    }
                }
            }
            CommonResponse response = new CommonResponse("获取成功");
            response.addNewDate("allNum", all);
            response.addNewDate("dfkNum", dfkNum);
            response.addNewDate("dfhNum", dfhNum);
            response.addNewDate("dshNum", dshNum);
            response.addNewDate("tkNum", tkNum);
            return response;
        } catch (Exception e) {
            logger.error("获取报错" + e.getMessage());
            return new CommonResponse("获取失败", 5, e);
        }
    }

    /**
     * 分页查询订单
     * @param req 分页条件
     * @return 订单列表
     */
    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    public CommonResponse getOrders(OrderSearchReq req) {
        int page = req.getPage() == 0 ? 0 : req.getPage() - 1;
        int pageSize = req.getPageSize() == 0 ? 10 : req.getPageSize();
        List<Orders> allOrders = orderServer.getAll(req);
        int total = allOrders.size();
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List<OrdersVo> ordersVos = new ArrayList<>();
        List<Orders> ordersList = orderServer.findOrderCriteria(page, pageSize, req);
        for (Orders orders : ordersList) {
            OrdersVo ordersVo = new OrdersVo();
            BeanUtils.copyProperties(orders, ordersVo);
            ordersVo.createBy = orders.createBy;
            ordersVo.createTime = orders.createTime;
            ordersVo.modifyTime = orders.modifyTime;
            List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByOrderId(orders.getId());
            for (OrderDetail orderDetail : orderDetails) {
                Commodity commodity = commodityServer.getCommodityAndDeleteById(orderDetail.getCommodityId());
                CommodityVo commodityVo = new CommodityVo();
                BeanUtils.copyProperties(commodity, commodityVo);
                commodityVo.setOrderNum(orderDetail.getOrderNum());
                commodityVo.setStatus(orderDetail.getStatus());
                commodityVo.setDetailId(orderDetail.getId());
                commodityVo.setRefundReason(orderDetail.getRefundReason());
                ordersVo.getOrderCommodities().add(commodityVo);
                if (orderDetail.getStatus() == 1) {
                    ordersVo.setIsRefund(1);
                }
            }
            ordersVos.add(ordersVo);
        }
        CommonResponse response = new CommonResponse("获取成功", "info", ordersVos);
        response.addNewDate("pageNum", page + 1);
        response.addNewDate("pageSize", pageSize);
        response.addNewDate("total", total);
        response.addNewDate("totalPage", totalPage);
        return response;
    }

    /**
     * 根据用户帐号和状态获取订单详情
     * @param userCode 用户帐号
     * @param status   状态
     * @return 订单详情
     */
    @RequestMapping(value = "/getOrderDetails", method = RequestMethod.GET)
    public CommonResponse getOrderDetails(@RequestParam("userCode") String userCode, @RequestParam("status") int status) {
        List<Orders> allOrders = orderServer.getOrdersByCreateBy(userCode);
        List<CommodityVo> commodityVos = new ArrayList<>();
        for (Orders orders : allOrders) {
            List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByOrderId(orders.getId());
            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.getStatus() != status) {
                    Commodity commodity = commodityServer.getCommodityAndDeleteById(orderDetail.getCommodityId());
                    CommodityVo commodityVo = new CommodityVo();
                    BeanUtils.copyProperties(commodity, commodityVo);
                    commodityVo.setStatus(orderDetail.getStatus());
                    commodityVo.setOrderNum(orderDetail.getOrderNum());
                    commodityVo.setDetailId(orderDetail.getId());
                    commodityVo.setRefundReason(orderDetail.getRefundReason());
                    commodityVo.createBy = orderDetail.createBy;
                    commodityVo.createTime = orderDetail.createTime;
                    commodityVo.lastmodifiedBy = orderDetail.lastmodifiedBy;
                    commodityVo.modifyTime = orderDetail.modifyTime;
                    commodityVos.add(commodityVo);
                }
            }
        }
        return new CommonResponse("获取成功", "info", commodityVos);
    }


    /**
     * 根据订单详情编号获取订单详情
     * @param id 订单详情编号
     * @return 订单详情
     */
    @RequestMapping(value = "/getOrderDetailById", method = RequestMethod.GET)
    public CommonResponse getOrderDetailById(@RequestParam("id") String id) {
        OrderDetail orderDetail =(OrderDetail) orderDetailServer.getObjectById(id);
        Commodity commodity =(Commodity) commodityServer.getObjectById(orderDetail.getCommodityId());
        CommodityVo commodityVo = new CommodityVo();
        BeanUtils.copyProperties(commodity, commodityVo);
        commodityVo.setStatus(orderDetail.getStatus());
        commodityVo.setOrderNum(orderDetail.getOrderNum());
        commodityVo.setDetailId(orderDetail.getId());
        commodityVo.setRefundReason(orderDetail.getRefundReason());
        commodityVo.createBy = orderDetail.createBy;
        commodityVo.createTime = orderDetail.createTime;
        commodityVo.lastmodifiedBy = orderDetail.lastmodifiedBy;
        commodityVo.modifyTime = orderDetail.modifyTime;
        commodityVo.setMoney(orderDetail.getMoney());
        return new CommonResponse("获取成功", "info", commodityVo);
    }


    @RequestMapping(value = "/exportOrder", method = RequestMethod.GET)
    public void excel(HttpServletResponse response, @RequestParam("ids") String ids) throws Exception {
        List<ExcelData> excelDatas = new ArrayList<>();
        ExcelData ordersExcel = new ExcelData();
        ordersExcel.setName("订单信息数据");
        List<String> titles = new ArrayList();
        titles.add("订单编号");
        titles.add("创建时间");
        titles.add("收货人");
        titles.add("收货人电话");
        titles.add("收货人区域");
        titles.add("收货人详细地址");
        titles.add("订单备注");
        titles.add("订单总金额");
        titles.add("红包抵扣金额");
        titles.add("支付手续费金额");
        titles.add("支付流水号");
        titles.add("支付日期");
        titles.add("支付类型(1.微信支付，2.支付宝支付)");
        titles.add("订单状态(0.待付款，1.已付款，2.待收货，3.已收货，4.退款/退货中，5.已退款，6.交易关闭)");
        titles.add("商品信息");
        ordersExcel.setTitles(titles);
        //添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        List<OrdersVo> ordersVos = new ArrayList<>();
        if (!StringUtil.isEmpty(ids)) {
            String[] id_list = ids.split(",");
            for (String id : id_list) {
                Orders orders = (Orders) orderServer.getObjectById(id);
                if (orders != null) {
                    OrdersVo ordersVo = new OrdersVo();
                    BeanUtils.copyProperties(orders, ordersVo);
                    ordersVo.createBy = orders.createBy;
                    ordersVo.createTime = orders.createTime;
                    ordersVo.modifyTime = orders.modifyTime;
                    List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByOrderId(orders.getId());
                    for (OrderDetail orderDetail : orderDetails) {
                        Commodity commodity = commodityServer.getCommodityAndDeleteById(orderDetail.getCommodityId());
                        CommodityVo commodityVo = new CommodityVo();
                        BeanUtils.copyProperties(commodity, commodityVo);
                        commodityVo.setOrderNum(orderDetail.getOrderNum());
                        commodityVo.setStatus(orderDetail.getStatus());
                        ordersVo.getOrderCommodities().add(commodityVo);
                    }
                    ordersVos.add(ordersVo);
                }
            }
        }
        for (int i = 0; i < ordersVos.size(); i++) {
            row = new ArrayList();
            row.add(ordersVos.get(i).getId());
            row.add(ordersVos.get(i).createTime);
            row.add(ordersVos.get(i).getReceiver());
            row.add(ordersVos.get(i).getPhone());
            row.add(ordersVos.get(i).getArea());
            row.add(ordersVos.get(i).getAddress());
            row.add(ordersVos.get(i).getDescription());
            row.add(ordersVos.get(i).getTotalMoney());
            row.add(ordersVos.get(i).getDeductionMonry());
            row.add(ordersVos.get(i).getFeeMoney());
            row.add(ordersVos.get(i).getPayNo());
            row.add(ordersVos.get(i).getPayTime());
            row.add(ordersVos.get(i).getPayType());
            row.add(ordersVos.get(i).getStatus());
            List<CommodityVo> orderCommodities = ordersVos.get(i).getOrderCommodities();
            String commodityList = "";
            for (int j = 0; j < orderCommodities.size(); j++) {
                if (j != (orderCommodities.size() - 1)) {
                    commodityList += "商品编号：" + orderCommodities.get(j).getId() + ";商品名称：" + orderCommodities.get(j).getName() + ";商品数量：" + orderCommodities.get(j).getOrderNum() + ";" + ";商品状态：" + orderCommodities.get(j).getStatus() + ";" + "\r\n";
                } else {
                    commodityList += "商品编号：" + orderCommodities.get(j).getId() + ";商品名称：" + orderCommodities.get(j).getName() + ";商品数量：" + orderCommodities.get(j).getOrderNum() + ";" + ";商品状态：" + orderCommodities.get(j).getStatus() + ";";
                }
            }
            row.add(commodityList);
            rows.add(row);
        }
        ordersExcel.setRows(rows);
        excelDatas.add(ordersExcel);
        String fileName = "用户订单信息" + DateUtils.dateToDateString(new Date(), DateUtils.ZHCN_DATATIMEF_STR) + ".xls";
        ExcelUtils.exportExcel(response, fileName, excelDatas);
    }

    @RequestMapping(value = "/alipay", method = RequestMethod.GET)
    public CommonResponse alipay(HttpServletResponse httpResponse, @RequestParam("id") String id) throws IOException {
        AlipayClient alipayClient = null;
        if (payDev.equals("dev")) {
            alipayClient = new DefaultAlipayClient(server_url, app_id, private_key_test, format, charset, alipay_public_key_test, sign_type);
        } else {
            alipayClient = new DefaultAlipayClient(server_url, app_id, private_key_prod, format, charset, alipay_public_key_prod, sign_type);
        }
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        Orders orders = (Orders) orderServer.getObjectById(id);
        //在公共参数中设置回跳和通知地址
        //alipayRequest.setReturnUrl(return_url);
        alipayRequest.setReturnUrl((notify_url + "?id=" + orders.getId()));
        String bizContent = "{";
        if (orders != null && orders.getStatus() == 0) {
            List<OrderDetail> orderDetails = orderDetailServer.getOrderDetailByOrderId(orders.getId());
            //填充业务参数
            bizContent += "\"out_trade_no\":\"" + orders.getId() + "\"," +
                    " \"total_amount\":\"" + orders.getTotalMoney() + "\"," +
                    " \"product_code\":\"QUICK_WAP_PAY\"" +
                    " ,\"subject\":\"达礼网商城订单\"}";

            alipayRequest.setBizContent(bizContent);
            String form = "";
            try {
                //调用SDK生成表单
                form = alipayClient.pageExecute(alipayRequest).getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            httpResponse.setContentType("text/html;charset=" + charset);
            httpResponse.getWriter().write(form);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            logger.info("提交支付成功");
            return new CommonResponse("提交支付成功");
        } else {
            logger.info("提交支付失败，订单不存在或者订单状态不正确");
            return new CommonResponse("提交支付失败，订单不存在或者订单状态不正确");
        }
    }

    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    public void notify(HttpServletResponse httpResponse, HttpServletRequest request, @RequestParam("id") String id) throws IOException {
        //获取请求
        Orders orders = (Orders) orderServer.getObjectById(id);
        String trade_no = request.getParameter("trade_no");
        orders.setPayType(2);
        orders.setPayTime(new Date());
        orders.setPayNo(trade_no);
        orders.setStatus(1);
        orderServer.save(orders);
        logger.info("支付成功");
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write("支付宝支付成功！3秒钟跳到主页");
        //设置3秒钟跳转
        if (payDev == "test") {
            httpResponse.setHeader("refresh", "3;url=http://114.116.12.160:8080/shopping/paySuccess");
        } else {
            httpResponse.setHeader("refresh", "3;url=http://www.dali5.com/shopping/paySuccess");
        }
    }

    @RequestMapping(value = "/aliRefund", method = RequestMethod.GET)
    public CommonResponse aliRefund(@RequestParam("id") String id, @RequestParam("detailId") String detailId) throws IOException, AlipayApiException {
        String out_request_no = DateUtils.dateToDateString(new Date(), DateUtils.DATATIMEF_STR_SEC);
        try {
            AlipayClient alipayClient = null;
            if (payDev.equals("dev")) {
                alipayClient = new DefaultAlipayClient(server_url, app_id, private_key_test, format, charset, alipay_public_key_test, sign_type);
            } else {
                alipayClient = new DefaultAlipayClient(server_url, app_id, private_key_prod, format, charset, alipay_public_key_prod, sign_type);
            }
            Orders orders = (Orders) orderServer.getObjectById(id);
            OrderDetail orderDetail =(OrderDetail) orderDetailServer.getObjectById(detailId);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizContent("{" +
                    "\"out_trade_no\":\"" + orders.getId() + "\"," +
                    "\"trade_no\":\"" + orders.getPayNo() + "\"," +
                    "\"refund_amount\":\"" + orderDetail.getMoney() + "\"," +

                    "\"out_request_no\":\"" + out_request_no + "\"," +
                    "\"refund_reason\":\"达礼网商城正常退款\"" +
                    " }");
            AlipayTradeRefundResponse response;
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                logger.info("支付宝退款成功");
                return new CommonResponse("支付宝退款成功");
            } else {
                //失败会返回错误信息
                logger.error("支付宝退款失败,原因：" + response.getSubMsg());
                return new CommonResponse("支付宝退款失败,原因：" + response.getSubMsg(), 9);
            }
        } catch (Exception e) {
            logger.error("支付宝退款失败");
            return new CommonResponse("支付宝退款失败", 9, e);
        }
    }

    @RequestMapping(value = "/wxpay", method = RequestMethod.GET)
    public void wxpay(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        Map<String, String> reqData = new HashMap<>();
        Orders orders = (Orders) orderServer.getObjectById(id);
        reqData.put("out_trade_no", orders.getId());
        reqData.put("trade_type", "MWEB");
        reqData.put("product_id", "1");
        reqData.put("body", "达理网商城商品下单");
        // 订单总金额，单位为分
        reqData.put("total_fee", ((int) (orders.getTotalMoney() * 100)) + "");
        logger.error(reqData.get("total_fee"));
        // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
        reqData.put("spbill_create_ip", "112.74.42.218");
        // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
        reqData.put("notify_url", "http://www.dali5.com:8182/daliweb/order/wxNotify");
        // 自定义参数, 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
        reqData.put("device_info", "WEB");
        // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
        reqData.put("attach", "");
        reqData.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://www.dali5.com\",\"wap_name\": \"达礼网商城商品\"}}");


        Map<String, String> responseMap = wxPay.unifiedOrder(reqData);
        String returnCode = responseMap.get("return_code");
        logger.error("responseMap:" + responseMap);
        String resultCode = responseMap.get("result_code");
        if (WXPayConstants.SUCCESS.equals(returnCode) && WXPayConstants.SUCCESS.equals(resultCode)) {
            String mweb_url = responseMap.get("mweb_url") + "&redirect_url=http%3A%2F%2Fwww.dali5.com:8182/daliweb/order/wxNotify";
            String result = HttpUtil.doGet(mweb_url);
            response.setContentType("text/html;charset=" + charset);
            response.getWriter().write(result);
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    /**
     * @param request
     * @param code
     * @return Map
     * @Description 微信浏览器内微信支付/公众号支付(JSAPI)
     */
    @RequestMapping(value = "wxgzpay", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse wxgzpay(HttpServletRequest request, String code,String id) {
        try {
            //页面获取openId接口
            String getopenid_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
            String param = "appid=wx8ca5f07c892b9380&secret=08b108a6e1cedd0b59ec90c0cbbaf911" + "&code=" + code + "&grant_type=authorization_code";
            //向微信服务器发送get请求获取openIdStr
            String openIdStr = HttpUtil.sendGet(getopenid_url, param);
            logger.error("------------------->>"+openIdStr);
            JSONObject jsonObject =JSONObject.parseObject(openIdStr);
            String openId= (String) jsonObject.get("openid");
            logger.error("------------------->>"+openId);
            Map<String, String> reqData = new HashMap<>();
            Orders orders = (Orders) orderServer.getObjectById(id);
            reqData.put("out_trade_no", orders.getId());
            reqData.put("trade_type", "JSAPI");
            reqData.put("product_id", "1");
            reqData.put("openid", openId);
            reqData.put("body", "达理网商城商品下单");
            // 订单总金额，单位为分
            reqData.put("total_fee", ((int) (orders.getTotalMoney() * 100)) + "");
            logger.error(reqData.get("total_fee"));
            // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
            reqData.put("spbill_create_ip", "112.74.42.218");
            // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
            reqData.put("notify_url", "http://www.dali5.com:8182/daliweb/order/wxNotify");
            // 自定义参数, 可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
            reqData.put("device_info", "WEB");
            // 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
            reqData.put("attach", "");
            reqData.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://www.dali5.com\",\"wap_name\": \"达礼网商城商品\"}}");
            Map<String, String> responseMap = wxPay.unifiedOrder(reqData);
            String returnCode = responseMap.get("return_code");
            logger.error("------------------->>"+responseMap.toString());
            //以下内容是返回前端页面的json数据
            String prepay_id = "";//预支付id
            if (returnCode.equals("SUCCESS")) {
                prepay_id = responseMap.get("prepay_id");
            }
            Map<String, String> payMap = new HashMap<String, String>();
            payMap.put("appId", "wx8ca5f07c892b9380");
            payMap.put("timeStamp", System.nanoTime() + "");
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap,"zmsjke873hs62hakw0lsuwhy61gwjsmd");
            payMap.put("paySign", paySign);
            return new CommonResponse("获取成功","info",payMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse("获取失败");
        }
    }



    @RequestMapping(value = "/wxNotify", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void payNotify(HttpServletRequest request,HttpServletResponse httpResponse) throws Exception{
        logger.error("微信支付回调");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        if(!StringUtil.isEmpty(resultxml)){
            logger.error("------------------->>"+resultxml);
            Map<String, String> notifyMap = MyWxPayUtil.xmlToMap(resultxml);
            logger.error(notifyMap.toString());
            if("SUCCESS".equals(notifyMap.get("result_code"))&&"SUCCESS".equals(notifyMap.get("return_code"))){
                Orders orders=(Orders) orderServer.getObjectById(notifyMap.get("out_trade_no"));
                if(orders.getStatus()==0) {
                    orders.setPayTime(new Date());
                    orders.setPayType(1);
                    orders.setPayNo(notifyMap.get("transaction_id"));
                    orders.setStatus(1);
                    orderServer.save(orders);
                    logger.info("支付成功");
                }
            }
        }else{
            httpResponse.setContentType("text/html;charset=UTF-8");
            httpResponse.getWriter().write("微信支付完成！3秒钟跳到主页");
            //设置3秒钟跳转
            if(payDev=="test"){
                httpResponse.setHeader("refresh", "3;url=http://114.116.12.160:8080/shopping/paySuccess");
            }else{
                httpResponse.setHeader("refresh", "3;url=http://www.dali5.com/shopping/paySuccess");
            }
        }
        outSteam.close();
        inStream.close();
    }

    @RequestMapping(value = "/wxRefund", method = RequestMethod.GET)
    public CommonResponse wxRefund(@RequestParam("orderId") String orderId,@RequestParam("detailId")String detailId) throws Exception {
        Orders orders = (Orders) orderServer.getObjectById(orderId);
        OrderDetail orderDetail=(OrderDetail) orderDetailServer.getObjectById(detailId);
        Map<String, String> reqData = new HashMap<>();
        // 商户订单号
        reqData.put("out_trade_no", orderId);
        // 授权码
        reqData.put("out_refund_no", orderId+new Random().nextInt(1000)+1);
        // 订单总金额，单位为分，只能为整数
        reqData.put("total_fee", ((int)(orders.getTotalMoney()*100))+"");
        //退款金额
        reqData.put("refund_fee", ((int)(orderDetail.getMoney()*100))+"");
        // 退款异步通知地址
        reqData.put("notify_url", "http://www.dali5.com:8182/daliweb/order/refund/notify");
        reqData.put("refund_fee_type", "CNY");
        Map<String, String> resultMap = wxPay.refund(reqData);
        logger.info(resultMap.toString());
        if(resultMap.get("result_code").equals("SUCCESS")){ ;
            return new CommonResponse("退款成功");
        }else{
            return new CommonResponse("退款失败");
        }
    }

    @RequestMapping(value = "/refund/notify",method = RequestMethod.GET)
    public void refundNotify(HttpServletRequest request) throws Exception {
        // 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
        // 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
        // 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
        // TODO 处理业务
        Map<String, String> requstInfoMap = wxPayClient.decodeRefundNotify(request);
    }

    @RequestMapping(value = "/testCalc",method = RequestMethod.GET)
    public void testCalc(@RequestParam("id")String id) throws Exception {
        Orders orders= (Orders) orderServer.getObjectById(id);
        calcProxyDetail(orders);
    }

    private void calcProxyDetail(Orders orders) {
        String orderUserCode=orders.getCreateBy();
        User user=userServer.getUserByUserCode(orders.createBy);
        String orderPostalCode=null;
        String[] orderPostalCodeList=null;
        if(null!=user){
             orderPostalCode=user.getPostalCode();
             orderPostalCodeList=orderPostalCode.split("-");
        }
        Proxy proxy=getRecommendProxyUser(orderUserCode);
        //推广代理计算
        if(null!=proxy){
            ProxyDetail proxyDetail=new ProxyDetail();
            proxyDetail.setBuyUserCode(orders.getCreateBy());
            proxyDetail.setCreateBy(proxy.createBy);
            proxyDetail.setOrderNo(orders.getId());
            proxyDetail.setType(0);
            proxyServer.saveProxyDetail(proxyDetail);
        }
        //国家地区代理计算
        List<Proxy> proxies=proxyServer.getProxiesByType(1);
        for(Proxy proxy1 : proxies){
            ProxyDetail proxyDetail=new ProxyDetail();
            proxyDetail.setBuyUserCode(orders.getCreateBy());
            proxyDetail.setCreateBy(proxy1.createBy);
            proxyDetail.setOrderNo(orders.getId());
            proxyDetail.setType(1);
            proxyServer.saveProxyDetail(proxyDetail);
        }
        //省份地区代理计算
        proxies=proxyServer.getProxiesByType(2);
        for(Proxy proxy1 : proxies){
            if((null!=orderPostalCodeList&&orderPostalCodeList.length>=1&&proxy1.getPostalCode().contains(orderPostalCodeList[0]))){
                ProxyDetail proxyDetail=new ProxyDetail();
                proxyDetail.setBuyUserCode(orders.getCreateBy());
                proxyDetail.setCreateBy(proxy1.createBy);
                proxyDetail.setOrderNo(orders.getId());
                proxyDetail.setType(1);
                proxyServer.saveProxyDetail(proxyDetail);
            }
        }
        //城市地区代理计算
        proxies=proxyServer.getProxiesByType(3);
        for(Proxy proxy1 : proxies){
            if(null!=orderPostalCodeList&&orderPostalCodeList.length>=2&&proxy1.getPostalCode().contains(orderPostalCodeList[1])){
                ProxyDetail proxyDetail=new ProxyDetail();
                proxyDetail.setBuyUserCode(orders.getCreateBy());
                proxyDetail.setCreateBy(proxy1.createBy);
                proxyDetail.setOrderNo(orders.getId());
                proxyDetail.setType(1);
                proxyServer.saveProxyDetail(proxyDetail);
            }
        }
        //县区地区代理计算
        proxies=proxyServer.getProxiesByType(4);
        for(Proxy proxy1 : proxies){
            if((null!=orderPostalCodeList&&orderPostalCodeList.length>=3&&proxy1.getPostalCode().contains(orderPostalCodeList[2]))){
                ProxyDetail proxyDetail=new ProxyDetail();
                proxyDetail.setBuyUserCode(orders.getCreateBy());
                proxyDetail.setCreateBy(proxy1.createBy);
                proxyDetail.setOrderNo(orders.getId());
                proxyDetail.setType(1);
                proxyServer.saveProxyDetail(proxyDetail);
            }
        }
    }

    private Proxy getRecommendProxyUser(String orderUserCode){
        String recommendUserCode=userServer.getRecommendByCreateBy(orderUserCode);
        if(!StringUtil.isEmpty(recommendUserCode)){
            Proxy proxy=proxyServer.getProxyByUserCode(recommendUserCode);
            if(null!=proxy){
                return proxy;
            }else{
                return getRecommendProxyUser(recommendUserCode);
            }
        }else{
            return null;
        }
    }
}
