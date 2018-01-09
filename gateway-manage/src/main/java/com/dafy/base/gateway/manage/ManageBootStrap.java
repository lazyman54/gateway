package com.dafy.base.gateway.manage;

import com.dafy.base.dubbo.spring.boot.autoconfigure.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/3
 */
@EnableDubbo
@SpringBootApplication
public class ManageBootStrap {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ManageBootStrap.class);
        springApplication.setWebEnvironment(true);
        springApplication.run(args);
    }
}
