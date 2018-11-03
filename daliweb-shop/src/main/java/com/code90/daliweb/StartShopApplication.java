package com.code90.daliweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 商城启动类
 * @author Ray Lin
 * @create 2018-09-12 21:51
 **/
@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.code90.daliweb")
@ImportResource("classpath:dubbo-server.xml")
public class StartShopApplication {
    public static void main(String[] args){
        SpringApplication.run(StartShopApplication.class,args);
    }
}
