package com.dafy.base.gateway.data;

import com.dafy.base.dubbo.spring.boot.autoconfigure.EnableDubbo;
import com.dafy.base.gateway.common.util.ContainerAwaitSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/17
 */
@EnableDubbo
@SpringBootApplication
public class DataBootStrap {

    private static final Logger logger = LoggerFactory.getLogger(DataBootStrap.class);


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplicationBuilder(DataBootStrap.class).build();

        springApplication.run(args);

        ContainerAwaitSingleton containerAwaitSingleton = ContainerAwaitSingleton.getInstance();
        containerAwaitSingleton.setLogger(logger);
        containerAwaitSingleton.addShutdownHook();
        containerAwaitSingleton.startDaemonAwaitThread();
    }
}
