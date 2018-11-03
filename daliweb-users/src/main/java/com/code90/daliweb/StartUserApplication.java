package com.code90.daliweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 用户启动类
 * @author Ray Lin
 * @create 2018-09-12 0:12
 **/
@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.code90.daliweb")
@ImportResource("classpath:dubbo-server.xml")
public class StartUserApplication {
    public static void main(String[] args){
        SpringApplication.run(StartUserApplication.class,args);
    }
}
