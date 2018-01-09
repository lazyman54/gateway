package com.dafy.base.gateway.common.util;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/28
 */
public class DafyExecutorService {

    private final static int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final static int DEFAULT_MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() << 1;
    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final ExecutorService executorService;

    private static volatile boolean isCreate = false;

    private DafyExecutorService(int corePoolSize, int maxPoolSize, ThreadFactory factory) {

        executorService = MoreExecutors.listeningDecorator(new ThreadPoolExecutor(corePoolSize, maxPoolSize, 2, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), factory));
        MoreExecutors.addDelayedShutdownHook(executorService, 10, TimeUnit.SECONDS);

        isCreate = true;
    }

    public static DafyExecutorService instance() {
        return instance(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE);
    }

    public static DafyExecutorService instance(int corePoolSize, int maxPoolSize) {
        return instance(corePoolSize, maxPoolSize, DEFAULT_THREAD_FACTORY);
    }

    public static DafyExecutorService instance(int corePoolSize, int maxPoolSize, ThreadFactory factory) {

        if (!isCreate) {
            synchronized (DafyExecutorService.class) {
                if (!isCreate) {
                    ExecutorServiceHolder.init(corePoolSize, maxPoolSize, factory);
                }
            }
        }
        return ExecutorServiceHolder.EXECUTOR_SERVICE;

    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    private static class ExecutorServiceHolder {
        private static DafyExecutorService EXECUTOR_SERVICE;

        private static void init(int corePoolSize, int maxPoolSize, ThreadFactory factory) {
            EXECUTOR_SERVICE = new DafyExecutorService(corePoolSize, maxPoolSize, factory);
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        private DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "dafy-base-thread-pool-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            thread.setDaemon(true);
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }

}
