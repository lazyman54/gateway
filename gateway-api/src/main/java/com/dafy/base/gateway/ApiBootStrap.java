package com.dafy.base.gateway;

import com.dafy.base.dubbo.spring.boot.autoconfigure.EnableDubbo;
import com.dafy.base.gateway.api.configuration.ProjectProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/3
 */
@EnableDubbo
@SpringBootApplication
@EnableConfigurationProperties(ProjectProperties.class)
@Slf4j
public class ApiBootStrap {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplicationBuilder(ApiBootStrap.class).build();
        application.setWebEnvironment(true);
        application.run(args);
    }


}
