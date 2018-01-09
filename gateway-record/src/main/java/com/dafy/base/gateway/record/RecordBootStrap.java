package com.dafy.base.gateway.record;

import com.dafy.base.dubbo.spring.boot.autoconfigure.EnableDubbo;
import com.dafy.base.nodepencies.util.ContainerAwaitSingleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/28
 */
@EnableKafka
@EnableDubbo
@SpringBootApplication
@Slf4j
public class RecordBootStrap {


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RecordBootStrap.class);
        application.setWebEnvironment(false);
        application.run(args);

        ContainerAwaitSingleton.getInstance().setLogger(log).addShutdownHook().startDaemonAwaitThread();
    }


}
