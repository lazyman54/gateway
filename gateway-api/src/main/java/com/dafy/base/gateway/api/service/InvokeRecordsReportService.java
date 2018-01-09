package com.dafy.base.gateway.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dafy.base.gateway.common.domain.stat.ServerInvokeInfoModel;
import com.dafy.base.nodepencies.model.Response;
import com.dafy.data.ob.dto.ObRequest;
import com.dafy.data.ob.server.rpc.IObServiceRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/22
 */
@Service
@Slf4j
public class InvokeRecordsReportService {

    @Reference(version = "1.0.0")
    private IObServiceRpc obServiceRpc;

    public void reportData(ServerInvokeInfoModel serverInvokeInfoModel) {


        ObRequest obRequest = new ObRequest();
        obRequest.setEvent("gateway_invoke");
        obRequest.setTimestamp(System.currentTimeMillis());
        obRequest.setBizApp("gateway");

        obRequest.setParams(serverInvokeInfoModel);
        Response commit;
        try {
            commit = obServiceRpc.commit(obRequest);
        } catch (Exception e) {
            log.error("访问记录上报出错，上报数据：[{}], 错误信息：{}", obRequest, e);
            return;
        }
        log.info("日志事件上报完成，事务id：[{}], 记录数据：{}", commit.getData(), serverInvokeInfoModel);
    }

}
