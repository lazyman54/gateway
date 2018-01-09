package com.dafy.base.gateway.api.filter;

import com.alibaba.fastjson.JSON;
import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.common.domain.constants.errorCode.ServerErrorCode;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.domain.enums.SwitchEnum;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.service.RpcInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 服务开关拦截器
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
@Component
@Order(50)
public class ServerSwitchFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ServerSwitchFilter.class);


    private final RpcInfoService rpcInfoService;

    @Autowired
    public ServerSwitchFilter(RpcInfoService rpcInfoService) {
        this.rpcInfoService = rpcInfoService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Server Switch filter start");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.debug("处理服务开关拦截");
        String appKey = request.getParameter("appKey");
        String url = request.getParameter("url");
        TransmitMapDTO rpcInfo;
        try {
            rpcInfo = rpcInfoService.getRpcInfo(appKey, url);
        } catch (DafyBaseException e) {
            response.getWriter().write(JSON.toJSONString(ResponseModel.newBuilder().code(e.getCode()).message(e.getMessage())));
            response.flushBuffer();
            return;
        }

        SwitchEnum enableSwitch = rpcInfo.getEnableSwitch();

        if (enableSwitch != null && SwitchEnum.OFF.equals(enableSwitch)) {
            response.getWriter().write(JSON.toJSONString(serverNoAvailableResponse()));
            response.flushBuffer();
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("Server Switch filter destroy");
    }


    private ResponseModel serverNoAvailableResponse() {
        ResponseModel.Builder builder = ResponseModel.newBuilder();
        builder.code(ServerErrorCode.SERVER_NOT_AVAILABLE);
        builder.message("服务暂时不可用，请稍后再试");
        return builder.build();
    }
}
