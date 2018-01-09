package com.dafy.base.gateway.api.filter;

import com.alibaba.fastjson.JSON;
import com.dafy.base.gateway.api.interceptor.HttpRequestWrapper;
import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.common.domain.constants.errorCode.ParamErrorCode;
import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.common.domain.enums.GatewayParamEnum;
import com.dafy.base.gateway.common.util.CollectionUtil;
import com.dafy.base.gateway.service.AppService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * app自动识别拦截器
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/10
 */
@Component
@Order(30)
public class AppRecognizeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AppRecognizeFilter.class);

    private AppRecognizeMapHolder appRecognizeMapHolder;

    private final AppService appService;

    private final static String IP_REGEX = "((?:(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d))\\.){3}(?:25[0-5]|2[0-4]\\d|(?:1\\d{2}|[1-9]?\\d)))";

    @Autowired
    public AppRecognizeFilter(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<AppServerDTO> list = appService.listAll();
        if (CollectionUtil.isEmpty(list)) {
            logger.warn("当前缓存中没有服务名与appKey的映射，请核查");
            list = new ArrayList<>(0);
        }
        appRecognizeMapHolder = new AppRecognizeMapHolder();
        // TODO: 2017/12/18 maojinlin 这里app应该支持实时添加，后续可以跟zookeeper打通，或者通过mq消息队列的方式来做
        appRecognizeMapHolder.init(list);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpRequestWrapper _request = (HttpRequestWrapper) request;

        logger.debug("处理app识别拦截");

        AppServerDTO appMap = null;
        try {
            appMap = appRecognizeMapHolder.get(_request.getServerName());
        } catch (Throwable throwable) {
            /**
             * 兼容有些业务线要通过ip的方式来访问，如果通过ip的方式访问，需要加serverName的字段来区分
             */
            if (Pattern.matches(IP_REGEX, _request.getServerName())) {
                try {
                    appMap = appRecognizeMapHolder.get(_request.getParameter("serverName"));
                } catch (Throwable throwable1) {
                }
            }
            if (appMap == null) {
                logger.warn("此请求无法追查到对应的appKey，无法继续");
                response.getWriter().write(JSON.toJSONString(appKeyMissResponse()));
                response.flushBuffer();
                return;
            }
        }

        if (StringUtils.isEmpty(_request.getParameter(GatewayParamEnum.APP_KEY.getKey()))) {
            String appKey = appMap.getAppKey();
            _request.addParam(GatewayParamEnum.APP_KEY.getKey(), appKey);
        }
        _request.addParam(GatewayParamEnum.SECRET_KEY.getKey(), appMap.getSecretKey());


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


    private ResponseModel appKeyMissResponse() {
        ResponseModel.Builder builder = ResponseModel.newBuilder();
        builder.code(ParamErrorCode.LACK_OF_PARAM);
        builder.message("缺少系统参数，请核查");
        return builder.build();
    }
}
