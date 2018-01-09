package com.dafy.base.gateway.api.service;

import com.dafy.base.gateway.api.domain.GatewayRpcParam;
import com.dafy.base.gateway.common.domain.constants.errorCode.ServerErrorCode;
import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.service.AppService;
import com.dafy.base.gateway.service.GatewayRpcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/12
 */
@Service
public class BaseRpcServer {

    private static final Logger logger = LoggerFactory.getLogger(BaseRpcServer.class);

    private final AppService appService;

    private final GatewayRpcImpl gatewayRpc;

    @Autowired
    public BaseRpcServer(AppService appService, GatewayRpcImpl gatewayRpc) {
        this.appService = appService;
        this.gatewayRpc = gatewayRpc;
    }

    public Object handleRpcServer(GatewayRpcParam param) throws Exception {
        AppServerDTO appServerDTO = appService.getByAppKey(param.getAppKey());
        if (appServerDTO == null) {
            throw new DafyBaseException(ServerErrorCode.SERVER_NOT_RECOGNIZE, "无法识别客户端标识，请检查appKey标识信息");
        }

        return gatewayRpc.doGateway(param);
    }

}
