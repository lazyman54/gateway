package com.dafy.base.gateway.api.filter;

import com.alibaba.fastjson.JSON;
import com.dafy.base.gateway.api.interceptor.HttpRequestWrapper;
import com.dafy.base.gateway.api.strategy.NoneUrlRequestHandlerStrategy;
import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.common.domain.constants.errorCode.ParamErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 参数合法性检验
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/10
 */
@Component
@Order(40)
public class ParamValidateFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ParamValidateFilter.class);

    @Autowired
    private NoneUrlRequestHandlerStrategy noneUrlRequestHandlerStrategy;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpRequestWrapper _request = (HttpRequestWrapper) request;
        logger.debug("处理参数校验拦截");

        Object urlParam = _request.getParameter("url");

        if (urlParam == null) {
            logger.warn("缺少url参数");

            if (!noneUrlRequestHandlerStrategy.isNoneUrlRequestAllow()) {
                response.getWriter().write(JSON.toJSONString(urlParamMissResponse()));
                response.flushBuffer();
                return;
            }
        }

        // TODO: 其他的必要参数校验
        /*这里应该还有一些其他的业务限制代码，可以在这里补充*/

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private ResponseModel urlParamMissResponse() {

        ResponseModel.Builder builder = ResponseModel.newBuilder();
        return builder.code(ParamErrorCode.LACK_OF_PARAM).message("缺少必要的参数").build();
    }
}
