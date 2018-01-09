package com.dafy.base.gateway.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dafy.base.gateway.common.domain.constants.errorCode.ServerErrorCode;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.domain.enums.AppLevelEnum;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.data.api.ITransmitMapRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/6
 */
@Service
public class RpcInfoService {

    private static final Logger logger = LoggerFactory.getLogger(RpcInfoService.class);

    //@Reference(version = "1.0.0", url = "dubbo://localhost:20880")
    @Reference(version = "1.0.0")
    private ITransmitMapRpc transmitMapRpc;

    public TransmitMapDTO getRpcInfo(String appKey, String url) throws DafyBaseException {

        TransmitMapDTO rpcInfo = transmitMapRpc.getTransmitMap(appKey, url);

        if (rpcInfo == null) {
            throw new DafyBaseException(ServerErrorCode.SERVER_NOT_ONLINE, "服务映射配置错误，请检查后重试");
        }

        return rpcInfo;
    }

    public List<TransmitMapDTO> listRpcInvocationInfo(Collection<String> appKeys, AppLevelEnum level) {
        logger.info("list rpc invocation info by level and appKeys, level:[{}], appKey:[{}]", level, appKeys);

        Collection<String> collection = AppLevelEnum.higherLevel(level);

        return transmitMapRpc.listByAppKeyAndLevel(appKeys, collection);
    }


    public List<TransmitMapDTO> listAllRpcInvocationInfo(int start, int length) {
        logger.info("list all apc invocation info");
        return transmitMapRpc.listByAppKeyAndLevel(null, null);
    }


}
