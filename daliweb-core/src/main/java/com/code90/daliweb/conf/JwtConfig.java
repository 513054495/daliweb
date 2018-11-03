/*package com.code90.daliweb.conf;

import com.code90.daliweb.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*//**
 * Jwt配置类
 * @author Ray Lin
 * @create 2018-09-09 14:03
 **//*
@Configuration
public class JwtConfig {
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/daliweb/*");
        return registrationBean;
    }
}*/
