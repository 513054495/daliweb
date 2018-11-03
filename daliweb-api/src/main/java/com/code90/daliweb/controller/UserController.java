package com.code90.daliweb.controller;

import com.code90.daliweb.domain.Recommend;
import com.code90.daliweb.domain.User;
import com.code90.daliweb.request.CommonRequest;
import com.code90.daliweb.request.user.*;
import com.code90.daliweb.response.CommonResponse;
import com.code90.daliweb.server.UserServer;
import com.code90.daliweb.utils.DateUtils;
import com.code90.daliweb.utils.IdUtils;
import com.code90.daliweb.utils.MD5Util;
import com.code90.daliweb.utils.StringUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * 用户控制类
 * @author Ray
 * @create 2018-05-28 0:14
 **/
@RestController
public class UserController {
    private static final Logger logger=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServer userServer;

    /**
     * 新增
     * @param req 用户信息
     * @return 新增结果
     */
    @RequestMapping(value = "/daliweb/user/addUser",method = RequestMethod.POST)
    public CommonResponse addUser(@RequestBody UserSaveReq req){
        try {
            User user=new User();
            BeanUtils.copyProperties(req,user);
            user.setDlwPsw(MD5Util.getMD5String(req.getDlwPsw()));
            user.setShareCode(IdUtils.createShareCode());
            userServer.save(user);
            logger.info("保存成功");
            return new CommonResponse("保存成功");
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 修改用户
     * @param req 修改信息
     * @return 修改结果
     */
    @RequestMapping(value="/daliweb/user/editUser",method = RequestMethod.POST)
    public CommonResponse updateUser(@RequestBody UserChangeReq req){
        try {
            User user=(User)userServer.getObjectById(req.getId());
            BeanUtils.copyProperties(req,user);
            userServer.save(user);
            logger.info("用户修改成功");
            return new CommonResponse("修改成功");
        }catch (Exception e){
            logger.error("修改失败，原因："+e.getMessage());
            return new CommonResponse("修改失败",2,e);
        }
    }

    /**
     * 修改密码
     * @param req 密码信息
     * @return 修改结果
     */
    @RequestMapping(value="/daliweb/user/editPsw",method = RequestMethod.POST)
    public CommonResponse updatPsw(@RequestBody UserChangePswReq req){
        try {
            User user=(User)userServer.getObjectById(req.getId());
            if(user!=null&&MD5Util.getMD5String(req.getOldDlwPsw()).equals(user.getDlwPsw())){
                user.setDlwPsw(MD5Util.getMD5String(req.getNewDlwPsw()));
            }else{
                logger.error("修改密码失败，旧密码不正确");
                return new CommonResponse("修改密码失败，旧密码不正确",2);
            }
            userServer.save(user);
            logger.info("用户修改密码成功");
            return new CommonResponse("修改密码成功");
        }catch (Exception e){
            logger.error("修改密码失败，原因："+e.getMessage());
            return new CommonResponse("修改密码失败",2,e);
        }
    }

    /**
     * 删除用户
     * @param ids 用户编号
     * @return 删除结果
     */
    @RequestMapping(value="/daliweb/user/delUser",method = RequestMethod.DELETE)
    public CommonResponse delUser(@RequestParam("ids") String ids){
        try {
            if(!StringUtil.isEmpty(ids)){
                String[] id_list=ids.split(",");
                for (String id : id_list){
                    User user=(User)userServer.getObjectById(id);
                    userServer.delete(user);
                }
                logger.info("用户删除成功");
                return new CommonResponse("删除成功");
            }else{
                logger.error("删除失败，用户编号不能为空");
                return new CommonResponse("删除失败，用户编号不能为空",3);
            }
        }catch (Exception e){
            logger.error("删除失败，原因："+e.getMessage());
            return new CommonResponse("删除失败",3,e);
        }
    }

    /**
     * 根据用户编号获取用户
     * @param id 用户编号
     * @return 用户信息
     */
    @RequestMapping(value="/daliweb/user/getUserById",method=RequestMethod.GET)
    public CommonResponse getUserById(@RequestParam("id") String id){
       User user=(User)userServer.getObjectById(id);
       if(user!=null){
           return new CommonResponse("获取用户成功","info",user);
       }else{
           return new CommonResponse("获取失败，用户不存在",5);
       }
    }

    /**
     * 根据用户帐号获取用户
     * @param userCode 用户帐号
     * @return 用户信息
     */
    @RequestMapping(value="/daliweb/user/getUserByUserCode",method=RequestMethod.GET)
    public CommonResponse getUserCodeById(@RequestParam("userCode") String userCode){
        User user=userServer.getUserByUserCode(userCode);
        if(user!=null){
            return new CommonResponse("获取用户成功","info",user);
        }else{
            return new CommonResponse("获取失败，用户不存在",5);
        }
    }

    /**
     * 根据用户帐号获取用户
     * @param userCode 用户帐号
     * @return 用户信息
     */
    @RequestMapping(value="/daliweb/user/getRecommendByUserCode",method=RequestMethod.GET)
    public CommonResponse getRecommendByUserCode(@RequestParam("userCode") String userCode){
        List<Recommend> recommends=userServer.getRecommendByUserCode(userCode);
        CommonResponse response= new CommonResponse("获取用户成功","info",recommends);
        response.addNewDate("totalFilend",recommends.size());
        return response;
    }

    /**
     * 分页查询用户
     * @param req 分页条件
     * @return 用户列表
     */
    @RequestMapping(value="/daliweb/user/getUsers",method=RequestMethod.GET)
    public CommonResponse getUsers(UserSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<User> allUsers=userServer.getAll(req);
        int total=allUsers.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<User> users=userServer.findUserCriteria(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",users);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 分页查询用户(荣誉板块)
     * @param req 分页条件
     * @return 用户列表
     */
    @RequestMapping(value="/daliweb/user/getUsersByHonor",method=RequestMethod.GET)
    public CommonResponse getUsersByHonor(UserSearchReq req){
        int page=req.getPage()==0?0:req.getPage()-1;
        int pageSize=req.getPageSize()==0?10:req.getPageSize();
        List<User> allUsers=userServer.getAll(req);
        int total=allUsers.size();
        int totalPage=total%pageSize==0?total/pageSize:total/pageSize+1;
        List<User> users=userServer.findUserCriteriaByHonor(page,pageSize,req);
        CommonResponse response= new CommonResponse("获取成功","info",users);
        response.addNewDate("pageNum",page+1);
        response.addNewDate("pageSize",pageSize);
        response.addNewDate("total",total);
        response.addNewDate("totalPage",totalPage);
        return response;
    }

    /**
     * 注册
     * @param req 注册信息
     * @return 注册结果
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResponse register(@RequestBody UserRegisterReq req){
        try {
            User user=new User();
            BeanUtils.copyProperties(req,user);
            user.setDlwPsw(MD5Util.getMD5String(req.getDlwPsw()));
            user.setShareCode(IdUtils.createShareCode());
            user.setUserName("达理网用户");
            user.setNickname("达理网用户");
            User recommendUser=userServer.getUserByShareCode(req.getShareCode());
            if(null!=recommendUser){
                Recommend recommend=new Recommend();
                recommend.createBy=req.getUserCode();
                recommend.setUserCode(recommendUser.getUserCode());
                userServer.saveRecommend(recommend);
            }
            userServer.save(user);
            user=userServer.getUserByUserCode(user.getUserCode());
            user.setDlwPsw("");
            logger.info("保存成功");
            CommonResponse response= createJwt(user.getUserCode(),"保存成功");
            response.addNewDate("info",user);
            return response;
        }catch (Exception e){
            logger.error("保存失败，原因："+e.getMessage());
            return new CommonResponse("保存失败",1,e);
        }
    }

    /**
     * 登录
     * @param req 登录信息
     * @return 登录结果+jwt信息
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResponse login(@RequestBody LoginReq req){
        if(StringUtil.isEmpty(req.getUserCode())&&StringUtil.isEmpty(req.getDlwPsw())) {
            logger.error("登录失败，帐号或密码不能为空");
            return new CommonResponse("登录失败，帐号或密码不能为空",4);
        }else {
            User user = userServer.getUserByUserCode(req.getUserCode());
            if (null != user) {
                System.out.println(MD5Util.getMD5String(req.getDlwPsw()));
                if (MD5Util.getMD5String(req.getDlwPsw()).equals(user.getDlwPsw())) {
                    if(user.getUserType()!=2&&req.getIsFront()!=1){
                        logger.error("登录失败，非管理员无法登录后台管理");
                        return new CommonResponse("登录失败，非管理员无法登录后台管理", 4);
                    }else if(user.getUserType()==2&&req.getIsFront()==1){
                        logger.error("登录失败，管理员无法登录");
                        return new CommonResponse("登录失败，管理员无法登录", 4);
                    }else{
                        logger.info("登录成功");
                        CommonResponse response=createJwt(req.getUserCode(),"登录成功");
                        response.addNewDate("info",user);
                        return response;
                    }
                }else{
                    logger.error("登录失败，帐号或密码不正确");
                    return new CommonResponse("登录失败，帐号或密码不正确", 4);
                }
            } else {
                logger.error("登录失败，用户不存在");
                return new CommonResponse("登录失败，用户不存在", 4);
            }
        }
    }

    /**
     * 文件上传
     * @param file 文件
     * @return
     */
    @RequestMapping(value = "daliweb/user/fileUpload",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse fileUpload(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            logger.error("上传失败，文件不能为空");
            return new CommonResponse("上传失败，文件不能为空",6);
        }
        String fileName = DateUtils.dateToDateString(new Date(),DateUtils.DATATIMEF_STR_MIS)+file.getOriginalFilename();
        String path = "./userPic" ;
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
    @RequestMapping(value = "daliweb/user/download",method = RequestMethod.GET)
    public void downLoad(HttpServletResponse response,String filePath){
        File file = new File("./userPic/"+filePath);

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

    private CommonResponse createJwt(String userCode,String message){
        String jwtToken = Jwts.builder().setSubject(userCode)
                .setExpiration(new Date(System.currentTimeMillis() + 2*60*60*1000))
                .signWith(SignatureAlgorithm.HS256, "blwjwtkey").compact();
        CommonResponse response=new CommonResponse(message,"authorization","Bearer "+jwtToken);
        return response;
    }
}
