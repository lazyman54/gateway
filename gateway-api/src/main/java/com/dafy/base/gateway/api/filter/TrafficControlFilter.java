package com.dafy.base.gateway.api.filter;

import com.alibaba.fastjson.JSON;
import com.dafy.base.gateway.api.configuration.ProjectProperties;
import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.common.domain.constants.errorCode.ServerErrorCode;
import com.dafy.base.gateway.common.domain.constants.errorCode.SystemErrorCode;
import com.dafy.base.gateway.service.RpcInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 此类用于做流量控制处理。。。
 * 单台服务器在任意一个时间点最多处理的请求总数<=512，单台服务器在任意一个时间点最多处理的单个url总数<128
 * 后期可以开启机器学习策略，对于访问耗时不同的url允许的单次最大值动态改变
 * <p>
 * <p>目前只对但机器限流，不对单个url限流，没啥大的意义</p>
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/7
 */
@Component
@Order(70)
public class TrafficControlFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TrafficControlFilter.class);

    /**
     * 机器拥堵时请求等待的最大时间
     */
    private static final int WAIT_TIME = 16;

    //private final TrafficMapHolder trafficMapHolder = new TrafficMapHolder();

    private final TrafficRateLimiter rateLimiter;

    private final ProjectProperties projectProperties;

    private final RpcInfoService rpcInfoService;

    public TrafficControlFilter(ProjectProperties projectProperties, RpcInfoService rpcInfoService) {
        this.projectProperties = projectProperties;
        this.rpcInfoService = rpcInfoService;
        this.rateLimiter = new TrafficRateLimiter();
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("traffic control filter init");
        //List<TransmitMapDTO> dtoList = rpcInfoService.listAllRpcInvocationInfo(0, 1);

        rateLimiter.init(null, projectProperties);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("处理流控拦截");

        try {
            if (rateLimiter.applyToPass(WAIT_TIME)) {
                chain.doFilter(request, response);
            } else {
                response.getWriter().write(JSON.toJSONString(maxTrafficLimit()));
                response.flushBuffer();
            }
        } finally {
            rateLimiter.passComplete();
        }

    }

    @Override
    public void destroy() {
        logger.info("traffic control filter destroy");
    }

    private int getMaxTrafficCount() {
        return 10;
    }

    private int getMaxUrlTrafficCount() {
        return 128;
    }


    private ResponseModel maxTrafficLimit() {
        ResponseModel.Builder builder = ResponseModel.newBuilder();

        builder.code(SystemErrorCode.MACHINE_BUSY);
        builder.message("系统繁忙，请休息会再重试");

        return builder.build();
    }

    private ResponseModel maxUrlTrafficLimit() {
        ResponseModel.Builder builder = ResponseModel.newBuilder();

        builder.code(ServerErrorCode.SERVER_BUSY);
        builder.message("系统繁忙，请稍后重试");
        return builder.build();
    }

}
