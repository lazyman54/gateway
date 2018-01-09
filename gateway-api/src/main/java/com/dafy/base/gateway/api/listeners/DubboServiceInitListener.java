package com.dafy.base.gateway.api.listeners;

import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.domain.enums.AppLevelEnum;
import com.dafy.base.gateway.common.util.CollectionUtil;
import com.dafy.base.gateway.common.util.DafyCommonStartInfoUtil;
import com.dafy.base.gateway.service.RpcInfoService;
import com.dafy.base.gateway.service.core.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * spring容器初始化之后需要预先初始化部分level比较高的rpc reference。
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/9/27
 */
@Component
public class DubboServiceInitListener implements ApplicationListener<ApplicationReadyEvent> {

    private final static Logger logger = LoggerFactory.getLogger(DubboServiceInitListener.class);

    private final RpcInfoService rpcInfoService;

    private final DubboService dubboService;

    private final ExecutorService executorService;

    public DubboServiceInitListener(RpcInfoService rpcInfoService, DubboService dubboService, ExecutorService executorService) {
        this.rpcInfoService = rpcInfoService;
        this.dubboService = dubboService;
        this.executorService = executorService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        List<TransmitMapDTO> transmitMapDTOS = rpcInfoService.listRpcInvocationInfo(Collections.singleton("sevend"), AppLevelEnum.valueOf("MIDDLE"));

        if (CollectionUtil.isEmpty(transmitMapDTOS)) {
            return;
        }

        CountDownLatch countDown = new CountDownLatch(transmitMapDTOS.size());

        for (TransmitMapDTO transmitMapDTO : transmitMapDTOS) {
            Invocation invocation = transmitMapDTO.getInvocation();

            executorService.execute(() -> {
                try {
                    dubboService.get(invocation);
                } catch (Exception e) {
                    logger.info("init rpc invocation fail, invocation:{}, error: {}", invocation, e);
                } finally {
                    countDown.countDown();
                }
            });
        }
        try {
            countDown.await();
            logger.info(DafyCommonStartInfoUtil.getStartedInfo01());
        } catch (InterruptedException e) {
            logger.error("wait for dubbo invoker init was interrupted, e:{}", e);
        }
    }
}
