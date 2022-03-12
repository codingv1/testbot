package com.axess.history.Intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *  InterceptorAdapterConfig
 *  @author  Yao
 *
 */
@Configuration
public class InterceptorAdapterConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private LoginInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}