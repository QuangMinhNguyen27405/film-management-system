package com.crm.config;

import com.crm.interceptor.AdminAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminAuthInterceptor adminAuthInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(adminAuthInterceptor)
//                .addPathPatterns("/admin/**");  // Apply interceptor to all /admin/** paths
//    }
}