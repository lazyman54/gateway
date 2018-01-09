package com.dafy.base.gateway.common.util;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/28
 */
public class DafyExecutorServiceTest {
    @Test
    public void testInstance() throws Exception {
        int concurrentNum = 1500;
        CountDownLatch countDownLatch = new CountDownLatch(concurrentNum);

        Set<String> address = new HashSet<>();
        for (int i = 0; i < concurrentNum; i++) {
            new Thread(() -> {
                ExecutorService executorService = DafyExecutorService.instance().getExecutorService();
                address.add(executorService.toString());
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(address.size());
    }

}