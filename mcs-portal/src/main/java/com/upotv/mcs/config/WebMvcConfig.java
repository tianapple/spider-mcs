package com.upotv.mcs.config;

import com.upotv.mcs.interceptor.McsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wow on 2017/6/26.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    //配置静态资源访问
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }

    //页面跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/").setViewName("login");
        super.addViewControllers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new McsInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/js", "/css", "/images")
                .excludePathPatterns("/");
        super.addInterceptors(registry);
    }
}
