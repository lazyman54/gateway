package com.dafy.base.gateway.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
@Configuration
public class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    private final ServerInvokeStatInterceptor serverInvokeStatInterceptor;

    @Autowired
    public MyWebMvcConfigurationSupport(ServerInvokeStatInterceptor serverInvokeStatInterceptor) {
        this.serverInvokeStatInterceptor = serverInvokeStatInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverInvokeStatInterceptor).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
