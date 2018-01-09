package com.dafy.base.gateway.api.holder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/10/16
 */
@Component
@Slf4j
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("init , {}", applicationContext);
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
