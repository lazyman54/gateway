package com.dafy.base.gateway.api.controller;

import com.dafy.base.gateway.api.domain.GatewayRpcParam;
import com.dafy.base.gateway.api.service.BaseRpcServer;
import com.dafy.base.gateway.api.service.ParamHandleServer;
import com.dafy.base.gateway.api.util.SignHelper;
import com.dafy.base.gateway.common.domain.constants.errorCode.ParamErrorCode;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/3
 */
@RestController
@RequestMapping("/base")
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);


    private final ParamHandleServer paramHandleServer;

    private final BaseRpcServer baseRpcServer;

    @Autowired
    public BaseController(ParamHandleServer paramHandleServer, BaseRpcServer baseRpcServer) {
        this.paramHandleServer = paramHandleServer;
        this.baseRpcServer = baseRpcServer;
    }

    @RequestMapping("/gateway/sign")
    public Object gatewayRequestSign(String url, String appKey, String version, long timestamp, String secretKey, String sign, HttpServletRequest request) throws Exception {
        logger.info("网关api接入服务(sign)，服务url：[{}], appKey:[{}], version:[{}], data:[{}]", url, appKey, version, request);
        if (StringUtils.isEmpty(url)) {
            throw new DafyBaseException(ParamErrorCode.LACK_OF_PARAM, "url can't be null");
        }
        SignHelper.builder().appKey(appKey).url(url).timestamp(timestamp).version(version).sign(sign).secretKey(secretKey).build().doSign().checkSign();

        GatewayRpcParam requestParam = paramHandleServer.handleParam(url, appKey, version, request);
        Object result = baseRpcServer.handleRpcServer(requestParam);

        logger.info("接入完成: {}", result);

        return result;
    }

    @RequestMapping("/gateway")
    public Object gatewayRequest(String url, String appKey, String version, HttpServletRequest request) throws Exception {
        logger.info("网关api接入服务，服务url：[{}], appKey:[{}], version:[{}], data:[{}]", url, appKey, version, request.toString());
        if (StringUtils.isEmpty(url)) {
            throw new DafyBaseException(ParamErrorCode.LACK_OF_PARAM, "url can't be null");
        }

        GatewayRpcParam requestParam = paramHandleServer.handleParam(url, appKey, version, request);
        Object result = baseRpcServer.handleRpcServer(requestParam);

        logger.debug("接入完成: {}", result);
        return result;
    }

    @RequestMapping("/gateway/adapter")
    public Object adapterGatewayRequest(String url, String appKey, String version, HttpServletRequest request) throws Exception {
        logger.info("#旧版本适配# 网关api接入服务，服务url：[{}], appKey:[{}], version:[{}], data:[{}]", url, appKey, version, request);
        if (StringUtils.isEmpty(url)) {
            throw new DafyBaseException(ParamErrorCode.LACK_OF_PARAM, "url can't be null");
        }

        GatewayRpcParam requestParam = paramHandleServer.handleParam(url, appKey, version, request);
        Object result = baseRpcServer.handleRpcServer(requestParam);

        logger.info("接入完成: {}", result);

        return result;
    }

}
