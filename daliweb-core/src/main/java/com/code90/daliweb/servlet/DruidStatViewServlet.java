package com.code90.daliweb.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Druid监控视图类
 * @author Ray
 * @create 2018-06-10 23:49
 **/
@WebServlet(urlPatterns = "/druid/*", initParams={
        @WebInitParam(name="allow",value=""),// IP白名单 (没有配置或者为空，则允许所有访问)
        @WebInitParam(name="deny",value=""),// IP黑名单 (存在共同时，deny优先于allow)
        @WebInitParam(name="loginUsername",value="daliweb"),// 用户名
        @WebInitParam(name="loginPassword",value="daliweb"),// 密码
        @WebInitParam(name="resetEnable",value="true")// 禁用HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet {
    private static final long serialVersionUID = 2359758657306626394L;
}
