package com.dafy.base.gateway.api.configuration;

import com.dafy.base.gateway.common.util.DafyExecutorService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.ExecutorService;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/20
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public ExecutorService executor() {
        return DafyExecutorService.instance(8, 16).getExecutorService();
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(BeanFactory beanFactory, ExecutorService executorService) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster(beanFactory);
        multicaster.setTaskExecutor(executorService);
        return multicaster;
    }

}
