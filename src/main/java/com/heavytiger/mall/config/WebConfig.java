package com.heavytiger.mall.config;

import com.heavytiger.mall.interceptor.CheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author heavytiger
 * @version 1.0
 * @description Web应用配置类
 * @date 2022/2/11 14:10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加鉴权拦截器，拦截所有请求，排除登录和注册相关的API调用
        registry.addInterceptor(new CheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/userExist");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
