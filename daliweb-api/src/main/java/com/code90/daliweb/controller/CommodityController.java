package com.code90.daliweb.controller;

import com.code90.daliweb.conf.RedisServer;
import com.code90.daliweb.domain.*;
import com.code90.daliweb.request.shop.*;
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
import java.util.UUID;

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
            commodity.createBy=req.getCreateBy();
            commodityServer.save(commodity);
            for(CommodityNorm commodityNorm : req.getCommodityNorms()){
                String uuId= UUID.randomUUID().toString();
                commodityNorm.setId(uuId);
                commodityNorm.setCommodityId(commodity.getId());
                commodityServer.saveCommodityNorms(commodityNorm);
                redisServer.setValue(uuId,commodityNorm.getTotalNum()+"");
            }
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 新增商品分类
     * @param req 商品分类信息
     * @return 新增结果
     */
    @RequestMapping(value = "/addCommodityType",method = RequestMethod.POST)
    public CommonResponse addCommodityType(@RequestBody CommodityTypeSaveReq req){
        try {
            CommodityType commodityType=new CommodityType();
            BeanUtils.copyProperties(req,commodityType);
            int maxId=commodityServer.getMaxCommodityTypeId();
            commodityType.setId(IdUtils.createSubjectId(maxId));
            commodityServer.saveCommodityType(commodityType);
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
            List<CommodityNorm> commodityNorms=commodityServer.getCommodityNormByCommodityId(req.getId());
            for (CommodityNorm commodityNorm : commodityNorms){
                redisServer.delValue(commodityNorm.getId());
                commodityNorm.setIsDelete(1);
                commodityServer.saveCommodityNorms(commodityNorm);
            }
            BeanUtils.copyProperties(req,commodity);
            commodityServer.save(commodity);
            for(CommodityNorm commodityNorm : req.getCommodityNorms()){
                String uuId= UUID.randomUUID().toString();
                commodityNorm.setId(uuId);
                commodityNorm.setCommodityId(commodity.getId());
                commodityServer.saveCommodityNorms(commodityNorm);
                redisServer.setValue(uuId,commodityNorm.getTotalNum()+"");
            }
            logger.info("商品修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }


    @RequestMapping(value="/editCommodityType",method = RequestMethod.POST)
    public CommonResponse updateCommodityType(@RequestBody CommodityTypeChangeReq req){
        try {
            CommodityType commodityType=commodityServer.getCommodityTypeById(req.getId());
            BeanUtils.copyProperties(req,commodityType);
            commodityServer.saveCommodityType(commodityType);
            logger.info("商品分类修改成功");
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
                    List<CommodityNorm> commodityNorms=commodityServer.getCommodityNormByCommodityId(id);
                    for (CommodityNorm commodityNorm : commodityNorms){
                        redisServer.delValue(commodityNorm.getId());
                        commodityNorm.setIsDelete(1);
                        commodityServer.saveCommodityNorms(commodityNorm);
                    }
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
     * 删除商品分类
     * @param ids 商品编号
     * @return 删除结果
     */
    @RequestMapping(value="/delCommodityType",method = RequestMethod.DELETE)
    public CommonResponse delCommodityType(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for(String id : id_list){
                   CommodityType commodityType=commodityServer.getCommodityTypeById(Integer.parseInt(id));
                   List<CommodityType> commodityTypes=commodityServer.getChildTypeByParentId(Integer.parseInt(id));
                   for(CommodityType commodityType1 : commodityTypes){
                       commodityServer.delCommodityType(commodityType1);
                   }
                   commodityServer.delCommodityType(commodityType);
                }
            }else{
                logger.error("删除失败，商品分类编号不能为空");
                return new CommonResponse("删除失败，商品分类编号不能为空",3);
            }
            logger.info("商品分类删除成功");
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
        List<CommodityVo> commodityVos=new ArrayList<>();
        if(!StringUtil.isEmpty(ids)){
            String[] id_list=ids.split(",");
            for (String id : id_list) {
                Commodity commodity = commodityServer.getCommodityAndDeleteById(id);
                if (commodity != null) {
                    CommodityVo commodityVo=new CommodityVo();
                    BeanUtils.copyProperties(commodity,commodityVo);
                    List<CommodityNorm> commodityNorms = commodityServer.getCommodityNormByCommodityId(commodity.getId());
                    for (CommodityNorm commodityNorm : commodityNorms) {
                        String totalNum = redisServer.getValue(commodityNorm.getId());
                        if (null != totalNum)
                            commodityNorm.setTotalNum(Integer.parseInt(totalNum));
                    }
                    commodityVo.setCommodityNorms(commodityNorms);
                    CommodityType commodityType=commodityServer.getCommodityTypeById(commodity.getTypeId());
                    if(null!=commodityType) {
                        commodityVo.setTypeName(commodityType.getName());
                    }
                    commodityVo.setParentTypeId(commodityType.getParentId());
                    commodityType=commodityServer.getCommodityTypeById(commodityType.getParentId());
                    if(null!=commodityType) {
                        commodityVo.setParentTypeName(commodityType.getName());
                    }
                    commodityVos.add(commodityVo);
                }
            }
            logger.error("获取商品成功");
            return new CommonResponse("获取商品成功", "info", commodityVos);
        }else{
            logger.error("获取失败，商品不存在");
            return new CommonResponse("获取失败，商品不存在",5);
        }
    }

    /**
     * 根据规格获取商品
     * @param ids 规格编号
     * @return 商品信息
     */
    @RequestMapping(value="/getCommodityByNormId",method=RequestMethod.GET)
    public CommonResponse getCommodityByNormId(@RequestParam("ids") String ids){
        List<CommodityVo> commodityVos=new ArrayList<>();
        if(!StringUtil.isEmpty(ids)){
            String[] id_list=ids.split(",");
            for (String id : id_list) {
                CommodityNorm commodityNorm=commodityServer.getCommodityNormById(id);
                Commodity commodity = commodityServer.getCommodityAndDeleteById(commodityNorm.getCommodityId());
                if (commodity != null) {
                    CommodityVo commodityVo=new CommodityVo();
                    BeanUtils.copyProperties(commodity,commodityVo);
                    String totalNum = redisServer.getValue(commodityNorm.getId());
                    if (null != totalNum)
                        commodityNorm.setTotalNum(Integer.parseInt(totalNum));
                    commodityVo.getCommodityNorms().add(commodityNorm);
                    CommodityType commodityType=commodityServer.getCommodityTypeById(commodity.getTypeId());
                    if(null!=commodityType) {
                        commodityVo.setTypeName(commodityType.getName());
                    }
                    commodityVo.setParentTypeId(commodityType.getParentId());
                    commodityType=commodityServer.getCommodityTypeById(commodityType.getParentId());
                    if(null!=commodityType) {
                        commodityVo.setParentTypeName(commodityType.getName());
                    }
                    commodityVos.add(commodityVo);
                }
            }
            logger.error("获取商品成功");
            return new CommonResponse("获取商品成功", "info", commodityVos);
        }else{
            logger.error("获取失败，商品不存在");
            return new CommonResponse("获取失败，商品不存在",5);
        }
    }

    /**
     * 获取商品分类
     * @param ids 商品分类编号
     * @return 商品分类信息
     */
    @RequestMapping(value="/getCommodityTypeById",method=RequestMethod.GET)
    public CommonResponse getCommodityTypeById(@RequestParam("ids") String ids){
        List<CommodityType> commoditieTypes=new ArrayList<>();
        if(!StringUtil.isEmpty(ids)){
            String[] id_list=ids.split(",");
            for (String id : id_list) {
                CommodityType commodityType = commodityServer.getCommodityTypeById(Integer.parseInt(id));
                if (commodityType != null) {
                    commoditieTypes.add(commodityType);
                }
            }
            logger.error("获取商品分类成功");
            return new CommonResponse("获取商品分类成功", "info", commoditieTypes);
        }else{
            logger.error("获取失败，商品分类不存在");
            return new CommonResponse("获取失败，商品分类不存在",5);
        }
    }

    /**
     * 根据父分类获取子分类
     * @param id 父分类编号
     * @return 子分类列表
     */
    @RequestMapping(value="/getChildCommodityTypeByParentId",method=RequestMethod.GET)
    public CommonResponse getChildCommodityTypeByParentId(@RequestParam("id") int id){
        List<CommodityType> commoditieTypes=commodityServer.getChildTypeByParentId(id);
        logger.error("获取商品子分类成功");
        return new CommonResponse("获取商品子分类成功", "info", commoditieTypes);
    }

    /**
     * 获取全部商品
     * @return 全部商品信息
     */
    @RequestMapping(value="/getAllCommodity",method=RequestMethod.GET)
    public CommonResponse getAllCommodity(){
        List<CommodityVo> commodityVos=new ArrayList<>();
        List<Commodity> commodities=commodityServer.getAllCommodity();
        for(Commodity commodity : commodities){
            CommodityVo commodityVo=new CommodityVo();
            BeanUtils.copyProperties(commodity,commodityVo);
            List<CommodityNorm> commodityNorms=commodityServer.getCommodityNormByCommodityId(commodity.getId());
            for(CommodityNorm commodityNorm : commodityNorms){
                String totalNum=redisServer.getValue(commodityNorm.getId());
                if(null!=totalNum)
                    commodityNorm.setTotalNum(Integer.parseInt(totalNum));
            }
            commodityVo.setCommodityNorms(commodityNorms);
            commodityVos.add(commodityVo);
        }
        logger.info("获取商品成功");
        return new CommonResponse("获取商品成功", "info", commodityVos);
    }

    /**
     * 获取全部商品分类
     * @return 全部商品分类信息
     */
    @RequestMapping(value="/getAllCommodityType",method=RequestMethod.GET)
    public CommonResponse getAllCommodityType(){
        List<Object> commodityTypeVos=new ArrayList<>();
        List<CommodityType> rootTypes=commodityServer.getRootCommodityType();
        for(CommodityType rootCommodityType : rootTypes){
            List<CommodityType> childType=commodityServer.getChildTypeByParentId(rootCommodityType.getId());
            if(childType.size()>0){
                CommodityTypeVo commodityTypeVo=new CommodityTypeVo();
                BeanUtils.copyProperties(rootCommodityType,commodityTypeVo);
                commodityTypeVo.setChildTypes(childType);
                commodityTypeVos.add(commodityTypeVo);
            }else{
                commodityTypeVos.add(rootCommodityType);
            }
        }
        logger.info("获取商品分类成功");
        return new CommonResponse("获取商品成功", "info", commodityTypeVos);
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
        List<CommodityVo> commodityVos=new ArrayList<>();
        List<Commodity> allCommodity=commodityServer.getAll(req);
        int total=allCommodity.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<Commodity> commodities=commodityServer.findCommodityCriteria(page,pageSize,req);
        for(Commodity commodity : commodities){
            CommodityVo commodityVo=new CommodityVo();
            BeanUtils.copyProperties(commodity,commodityVo);
            List<CommodityNorm> commodityNorms=commodityServer.getCommodityNormByCommodityId(commodity.getId());
            for(CommodityNorm commodityNorm : commodityNorms){
                String totalNum=redisServer.getValue(commodityNorm.getId());
                if(null!=totalNum)
                    commodityNorm.setTotalNum(Integer.parseInt(totalNum));
            }
            commodityVo.setCommodityNorms(commodityNorms);
            CommodityType commodityType=commodityServer.getCommodityTypeById(commodity.getTypeId());
            if(null!=commodityType) {
                commodityVo.setTypeName(commodityType.getName());
            }
            commodityVo.setParentTypeId(commodityType.getParentId());
            commodityType=commodityServer.getCommodityTypeById(commodityType.getParentId());
            if(null!=commodityType) {
                commodityVo.setParentTypeName(commodityType.getName());
            }
            commodityVos.add(commodityVo);
        }
        CommonResponse response= new CommonResponse("获取成功","info",commodityVos);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 置顶商品
     * @param id 商品编号
     * @param isTop 是否置顶
     * @return 修改结果
     */
    @RequestMapping(value="/topCommodity",method = RequestMethod.GET)
    public CommonResponse topCommodity(@RequestParam("id")String id,@RequestParam("isTop")int isTop ){
        try {
            Commodity commodity= (Commodity) commodityServer.getObjectById(id);
            if(isTop==1) {
                int maxLevel = commodityServer.getMaxLevel();
                commodity.setLevel(maxLevel + 1);
                commodity.setIsTop(1);
                commodityServer.save(commodity);
                logger.info("商品置顶成功");
                return new CommonResponse("商品置顶成功");
            }else{
                commodity.setLevel(0);
                commodity.setIsTop(0);
                commodityServer.save(commodity);
                logger.info("商品取消置顶成功");
                return new CommonResponse("商品取消置顶成功");
            }
        }catch (Exception e){
            logger.error("置顶失败，原因："+e.getMessage());
            return new CommonResponse("置顶失败",2,e);
        }
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
