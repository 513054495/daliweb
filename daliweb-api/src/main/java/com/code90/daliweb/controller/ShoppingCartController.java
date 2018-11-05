package com.code90.daliweb.controller;

import com.code90.daliweb.conf.RedisServer;
import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.shop.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.CommodityServer;
import com.code90.daliweb.server.ShoppingCartServer;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 购物车控制类
 * @author Ray Lin
 * @create 2018-09-15 23:41
 **/
@RestController
@RequestMapping("/daliweb/shoppingCart")
public class ShoppingCartController {
    private static final Logger logger=LoggerFactory.getLogger(ShoppingCartController.class);
    @Autowired
    private ShoppingCartServer shoppingCartServer;
    @Autowired
    private CommodityServer commodityServer;
    @Autowired
    private RedisServer redisServer;

    /**
     * 加入购物车
     * @param req 购物车信息
     * @return 生成结果
     */
    @RequestMapping(value = "/addShoppingCart",method = RequestMethod.POST)
    public CommonResponse addShoppingCart(@RequestBody ShoppingCartSaveReq req){
        try {
            ShoppingCart shoppingCart=shoppingCartServer.getShoppingCartByCommodityIdAndcreateBy(req.getCommodityId(),req.getCreateBy(),req.getSpecification());
            if(null!=shoppingCart){
                shoppingCart.setNum(shoppingCart.getNum()+req.getNum());
            }else{
                shoppingCart=new ShoppingCart();
                BeanUtils.copyProperties(req,shoppingCart);
                shoppingCart.createBy=req.getCreateBy();
            }
            shoppingCartServer.save(shoppingCart);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 购物车数量变更
     * @param ids 订单编号
     * @param nums 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editShoppingCart",method = RequestMethod.GET)
    public CommonResponse editShoppingCart(@RequestParam("ids")String ids, @RequestParam("nums") String nums){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                String[] num_list=nums.split(",");
                for(int i=0;i<id_list.length;i++){
                    ShoppingCart shoppingCart=(ShoppingCart) shoppingCartServer.getObjectById(id_list[i]);
                    shoppingCart.setNum(Integer.parseInt(num_list[i]));
                    shoppingCartServer.save(shoppingCart);
                }
            }else{
                logger.error("修改失败，购物车编号不能为空");
                return new CommonResponse("修改失败，购物车编号不能为空",3);
            }
            logger.info("购物车信息修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 删除购物车信息
     * @param ids 购物车编号
     * @return 删除结果
     */
    @RequestMapping(value="/delShoppingCart",method = RequestMethod.DELETE)
    public CommonResponse delShoppingCart(@RequestParam("ids") String ids,@RequestParam("userCode")String userCode,@RequestParam("specifications") String specifications){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                String[] specification_list=specifications.split(",");
                for(int i=0;i<id_list.length;i++){
                    ShoppingCart shoppingCart=shoppingCartServer.getShoppingCartByCommodityIdAndcreateBy(id_list[i],userCode,specification_list[i]);
                    shoppingCartServer.delete(shoppingCart);
                }
            }else{
                logger.error("删除失败，购物车编号不能为空");
                return new CommonResponse("删除失败，购物车编号不能为空",3);
            }
            logger.info("购物车信息删除成功");
            return new CommonResponse("删除成功");
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 分页查询购物车
     * @param req 分页条件
     * @return 购物车列表
     */
    @RequestMapping(value="/getShoppingCarts",method=RequestMethod.GET)
    public CommonResponse getShoppingCarts(ShoppingCartSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<ShoppingCart> allShoppingCarts=shoppingCartServer.getAll(req);
        int total=allShoppingCarts.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<ShoppingCart> shoppingCarts=shoppingCartServer.findOrderCriteria(page,pageSize,req);
        List<CommodityVo> commodityVos=new ArrayList<>();
        for(ShoppingCart shoppingCart:shoppingCarts){
            CommodityVo commodityVo=new CommodityVo();
            Commodity commodity=(Commodity)commodityServer.getObjectById(shoppingCart.getCommodityId());
            if(commodity!=null) {
                BeanUtils.copyProperties(commodity, commodityVo);
                commodityVo.createBy = shoppingCart.createBy;
                commodityVo.createTime = shoppingCart.createTime;
                commodityVo.modifyTime = shoppingCart.modifyTime;
                commodityVo.setOrderNum(shoppingCart.getNum());
                commodityVo.setDetailId(shoppingCart.getId());
                String str_num=redisServer.getValue(commodityVo.getId());
                if(!StringUtil.isEmpty(str_num)){
                    commodityVo.setTotalNum(Integer.parseInt(str_num));
                }
                commodityVo.setSpecification(shoppingCart.getSpecification());
                commodityVos.add(commodityVo);
            }
        }
        CommonResponse response= new CommonResponse("获取成功","info",commodityVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }
}
