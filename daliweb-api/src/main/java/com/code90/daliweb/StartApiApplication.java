package com.code90.daliweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 客户端启动类
 * @author Ray
 * @create 2018-05-28 0:14
 **/
@SpringBootApplication
@ImportResource("classpath:dubbo-consumer.xml")
public class StartApiApplication {
    public static void main(String[] args){
        SpringApplication.run(StartApiApplication.class,args);
    }
}
