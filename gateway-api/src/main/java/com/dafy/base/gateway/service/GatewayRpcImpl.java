package com.dafy.base.gateway.service;

import com.dafy.base.gateway.api.domain.GatewayRpcParam;
import com.dafy.base.gateway.common.domain.ParamDefinition;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.service.impl.InvokerServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lazyman
 * @version v1.0
 */
@Service
public class GatewayRpcImpl {

    private static final Logger logger = LoggerFactory.getLogger(GatewayRpcImpl.class);
    private static final Pattern PATTERN = Pattern.compile("/(\\w+)$");

    private final InvokerServiceFactory invokerServiceFactory;
    private final RpcInfoService rpcInfoService;


    @Autowired
    public GatewayRpcImpl(InvokerServiceFactory rpcInvokeServiceCache, RpcInfoService rpcInfoService) {
        this.invokerServiceFactory = rpcInvokeServiceCache;
        this.rpcInfoService = rpcInfoService;
    }

    public Object doGateway(GatewayRpcParam param) throws Exception {

        TransmitMapDTO transmitMap = rpcInfoService.getRpcInfo(param.getAppKey(), param.getUrl());
        logger.info("执行此次调用的url map为：" + transmitMap);
        Boolean urlAdapter = transmitMap.getUrlAdapter();
        if (urlAdapter != null && urlAdapter) {
            for (ParamDefinition paramDef : transmitMap.getInvocation().getParameterDefinition()) {
                if (paramDef.getFromUrl()) {
                    param.getData().put(paramDef.getParamName(), getUrlParam(param.getUrl()));
                    break;
                }
            }
        }

        InvokeService rpcInvokeService = invokerServiceFactory.getInvokeService(transmitMap.getProtocol());
        Object ob = transmitMap.getSync() ?
                rpcInvokeService.syncInvoke(transmitMap.getInvocation(), param.getData()) :
                rpcInvokeService.asyncInvoke(transmitMap.getInvocation(), param.getData());

        cleanResult(ob);
        return ob;

    }

    private String getUrlParam(String url) {
        Matcher matcher = PATTERN.matcher(url);
        String paramVal = "";
        while (matcher.find()) {
            paramVal = matcher.group(1);
        }
        return paramVal;

    }


    /**
     * 清楚返回结果了dubbo加入的class字段。
     *
     * @param obj dubbo调用的返回结果
     */
    private void cleanResult(Object obj) {
        if (obj instanceof Map) {
            cleanClassField((Map) obj);
            ((Map) obj).forEach((key, value) -> cleanResult(value));
        } else if (obj instanceof Collection) {
            ((Collection) obj).forEach(this::cleanResult);
        }

    }

    private void cleanClassField(Map paramMap) {
        String eraseKey = "class";
        if (paramMap.containsKey(eraseKey)) {
            paramMap.remove("class");
        }
    }


}
