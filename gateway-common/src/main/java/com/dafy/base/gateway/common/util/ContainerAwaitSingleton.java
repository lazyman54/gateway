package com.dafy.base.gateway.common.util;

import org.slf4j.Logger;

/**
 * dubbo服务启动监听线程
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/4
 */
public class ContainerAwaitSingleton {

    private volatile boolean stopAwait = false;

    private Logger logger;

    private ContainerAwaitSingleton() {
    }

    public static ContainerAwaitSingleton getInstance() {
        return ContainerAwaitSingletonHolder.containerAwaitSingleton;
    }

    private static class ContainerAwaitSingletonHolder {
        private static final ContainerAwaitSingleton containerAwaitSingleton = new ContainerAwaitSingleton();
    }


    public void setLogger(Logger logger) {
        this.logger = logger;
    }


    public void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (logger != null) {
                logger.warn("server has been shutdown!");
            }
            stopAwait = true;
        }));
    }

    public void startDaemonAwaitThread() {
        Thread daemonAwaitThread = new Thread(() -> {
            while (!stopAwait) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    if (logger != null) {
                        logger.error("gateway-api container has been interrupted! ex:{}", e);
                    }
                }
            }
        });
        daemonAwaitThread.setName("contain-dubbo");
        daemonAwaitThread.setContextClassLoader(getClass().getClassLoader());
        daemonAwaitThread.setDaemon(false);
        daemonAwaitThread.start();
    }


}
