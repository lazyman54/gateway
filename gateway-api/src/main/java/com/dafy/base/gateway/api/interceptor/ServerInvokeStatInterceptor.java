package com.dafy.base.gateway.api.interceptor;

import com.dafy.base.gateway.api.advice.ExceptionHandlerAdvice;
import com.dafy.base.gateway.api.strategy.ServerInvokeStatStrategy;
import com.dafy.base.gateway.common.domain.enums.GatewayParamEnum;
import com.dafy.base.gateway.common.domain.enums.InvokeStateEnum;
import com.dafy.base.gateway.common.domain.stat.ServerInvokeInfoModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
@Component
public class ServerInvokeStatInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ServerInvokeStatInterceptor.class);

    @Autowired
    private ServerInvokeStatStrategy serverInvokeStatStrategy;

    private ThreadLocal<Long> timeStat = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpRequestWrapper.class.isInstance(request)) {
            timeStat.set(System.currentTimeMillis());
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (HttpRequestWrapper.class.isInstance(request)) {
            serverInvokeStatStrategy.add(builderModel((HttpRequestWrapper) request, InvokeStateEnum.SUCCESS, null));
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String errorMessage = ExceptionHandlerAdvice.ERROR_MESSAGE_THREAD_LOCAL.get();

        if (StringUtils.isNotBlank(errorMessage)) {
            ExceptionHandlerAdvice.ERROR_MESSAGE_THREAD_LOCAL.remove();
            if (timeStat.get() != null) {
                serverInvokeStatStrategy.add(builderModel((HttpRequestWrapper) request, InvokeStateEnum.EXCEPTION, errorMessage));
            }
        }
    }


    private ServerInvokeInfoModel builderModel(HttpRequestWrapper request, InvokeStateEnum state, String exMessage) {
        long current = System.currentTimeMillis(), beginTime = timeStat.get();
        timeStat.remove();
        long invokeTime = current - beginTime;

        ServerInvokeInfoModel.ServerInvokeInfoModelBuilder builder = ServerInvokeInfoModel.builder();

        return builder.startTime(new Date(beginTime)).endTime(new Date(current)).duration(invokeTime).stateEnum(state)
                .appKey(request.getParameter(GatewayParamEnum.APP_KEY.getKey())).url(request.getParameter(GatewayParamEnum.URL.getKey())).exception(exMessage).build();

    }


}
