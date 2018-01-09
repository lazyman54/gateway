package com.dafy.base.gateway.api.strategy;

import com.dafy.base.gateway.api.service.InvokeRecordsReportService;
import com.dafy.base.gateway.common.domain.stat.ServerInvokeInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
@Component
public class ServerInvokeStatStrategy {

    private static final Logger logger = LoggerFactory.getLogger(ServerInvokeStatStrategy.class);

    private final InvokeRecordsReportService invokeRecordsReportService;

    private final ExecutorService executorService;

    public ServerInvokeStatStrategy(InvokeRecordsReportService invokeRecordsReportService, ExecutorService executorService) {
        this.invokeRecordsReportService = invokeRecordsReportService;
        this.executorService = executorService;
    }

    public void add(ServerInvokeInfoModel serverInvokeInfoModel) {
        logger.info("服务调用信息：[{}]", serverInvokeInfoModel);
        executorService.execute(() -> invokeRecordsReportService.reportData(serverInvokeInfoModel));
    }

}
