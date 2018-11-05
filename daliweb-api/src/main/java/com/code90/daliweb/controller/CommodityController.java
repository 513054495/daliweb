package com.code90.daliweb.controller;

import com.code90.daliweb.conf.RedisServer;
import com.code90.daliweb.domain.Commodity;
import com.code90.daliweb.request.shop.CommodityChangeReq;
import com.code90.daliweb.request.shop.CommoditySaveReq;
import com.code90.daliweb.request.shop.CommoditySearchReq;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.CommodityServer;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.IdUtils;
import com.code90.daliweb.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品控制类
 * @author Ray Lin
 * @create 2018-09-12 22:43
 **/
@RestController
@RequestMapping("/daliweb/commodity")
public class CommodityController {
    private static final Logger logger=LoggerFactory.getLogger(CommodityController.class);
    @Autowired
    private CommodityServer commodityServer;
    @Autowired
    private RedisServer redisServer;

    /**
     * 新增商品
     * @param req 商品信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addCommodity",method = RequestMethod.POST)
    public CommonResponse addCommodity(@RequestBody CommoditySaveReq req){
        try {
            Commodity commodity=new Commodity();
            BeanUtils.copyProperties(req,commodity);
            String id=IdUtils.createCommodityId();
            commodity.setId(id);
            commodityServer.save(commodity);
            redisServer.setValue(id,commodity.getTotalNum()+"");
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改商品
     * @param req 修改商品
     * @return 修改结果
     */
    @RequestMapping(value="/editCommodity",method = RequestMethod.POST)
    public CommonResponse updateCommodity(@RequestBody CommodityChangeReq req){
        try {
            Commodity commodity=(Commodity)commodityServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,commodity);
            commodityServer.save(commodity);
            redisServer.setValue(req.getId(),commodity.getTotalNum()+"");
            logger.info("商品修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 商品上下架
     * @param ids 商品编号
     * @param status 状态
     * @return 修改结果
     */
    @RequestMapping(value="/editStatus",method = RequestMethod.GET)
    public CommonResponse updateStatus(@RequestParam("ids")String ids,@RequestParam("status") int status){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for(String id : id_list){
                    Commodity commodity=(Commodity)commodityServer.getObjectById(id);
                    commodity.setStatus(status);
                    commodityServer.save(commodity);
                }
            }else{
                logger.error("修改失败，商品编号不能为空");
                return new CommonResponse("修改失败，商品编号不能为空",3);
            }
            logger.info("商品修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 删除商品
     * @param ids 商品编号
     * @return 删除结果
     */
    @RequestMapping(value="/delCommodity",method = RequestMethod.DELETE)
    public CommonResponse delCommodity(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for(String id : id_list){
                    Commodity commodity=(Commodity)commodityServer.getObjectById(id);
                    commodity.setDeleted(1);
                    commodityServer.save(commodity);
                }
            }else{
                logger.error("删除失败，商品编号不能为空");
                return new CommonResponse("删除失败，商品编号不能为空",3);
            }
            logger.info("商品删除成功");
            return new CommonResponse("删除成功");
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 获取商品
     * @param ids 商品编号
     * @return 商品信息
     */
    @RequestMapping(value="/getCommodityById",method=RequestMethod.GET)
    public CommonResponse getCommodityById(@RequestParam("ids") String ids){
        List<Commodity> commodities=new ArrayList<>();
        if(!StringUtil.isEmpty(ids)){
            String[] id_list=ids.split(",");
            for (String id : id_list) {
                Commodity commodity = commodityServer.getCommodityAndDeleteById(id);
                if (commodity != null) {
                    String str_num=redisServer.getValue(id);
                    if(!StringUtil.isEmpty(str_num)){
                        commodity.setTotalNum(Integer.parseInt(str_num));
                    }
                    commodities.add(commodity);
                }
            }
            logger.error("获取商品成功");
            return new CommonResponse("获取商品成功", "info", commodities);
        }else{
            logger.error("获取失败，商品不存在");
            return new CommonResponse("获取失败，商品不存在",5);
        }
    }

    /**
     * 分页查询商品
     * @param req 分页条件
     * @return 商品列表
     */
    @RequestMapping(value="/getCommodities",method=RequestMethod.GET)
    public CommonResponse getUsers(CommoditySearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<Commodity> allCommodity=commodityServer.getAll(req);
        int total=allCommodity.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Commodity> commodities=commodityServer.findCommodityCriteria(page,pageSize,req);
        for(Commodity commodity : commodities){
            String str_num=redisServer.getValue(commodity.getId());
            if(!StringUtil.isEmpty(str_num)){
                commodity.setTotalNum(Integer.parseInt(str_num));
            }
        }
        CommonResponse response= new CommonResponse("获取成功","info",commodities);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 文件上传
     * @param file 文件
     * @return
     */
    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse fileUpload(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            logger.error("上传失败，文件不能为空");
            return new CommonResponse("上传失败，文件不能为空",6);
        }
        String fileName = DateUtils.dateToDateString(new Date(),DateUtils.DATATIMEF_STR_MIS)+file.getOriginalFilename();
        String path = "./commodityPic" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(dest));
            out.write(file.getBytes());
            out.close();
            logger.info("上传成功");
            return new CommonResponse("上传成功","fileName",dest.getPath());
        } catch (Exception e) {
            logger.error("上传失败，原因："+e.getMessage());
            return new CommonResponse("上传失败",6,e);
        }
    }

    /**
     * 下载图片
     * @param response
     * @param filePath 文件路径
     */
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void downLoad(HttpServletResponse response, String filePath){
        File file = new File("./commodityPic/"+filePath);

        if(file.exists()){ //判断文件父目录是否存在
            //response.setContentType("application/image/jpeg");  --强制下载
            //response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName()); --强制下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                try{
                    bis.close();
                    fis.close();
                }catch(Exception e){
                    logger.error("流关闭出错，原因："+e.getMessage());
                }
                logger.info("文件下载成功");
            } catch (Exception e) {
                logger.error("文件下载出错，原因："+e.getMessage());
            }
        }else{
            logger.error("文件不存在");
        }
    }
}
