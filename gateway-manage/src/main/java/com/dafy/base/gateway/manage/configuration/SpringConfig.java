package com.dafy.base.gateway.manage.configuration;

import com.dafy.base.gateway.manage.spring.DafyConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/11
 */
/*@Configuration*/
public class SpringConfig {

    @Bean
    public ConversionService conversionService(GenericConversionService genericConversionService) {
        DafyConversionService dafyConversionService = new DafyConversionService(genericConversionService);
        dafyConversionService.init();
        return dafyConversionService;
    }

}
