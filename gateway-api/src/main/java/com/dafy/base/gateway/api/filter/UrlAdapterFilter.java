package com.dafy.base.gateway.api.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * URL重写，适配老的url，无缝转移到新的url上
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/6
 */
@Component
@Order(20)
public class UrlAdapterFilter implements Filter {

    private static final String GATEWAY_URL = "/base/gateway";
    private static final String GATEWAY_URL_ADAPTER = "/base/gateway/adapter";

    private static final Logger logger = LoggerFactory.getLogger(TrafficControlFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("UrlAdapterFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        logger.debug("处理url适配拦截器");

        String originUrl = httpServletRequest.getRequestURI();

        if (StringUtils.contains(originUrl, GATEWAY_URL)) {
            chain.doFilter(request, response);
        } else {
            RequestDispatcher dispatcher = httpServletRequest.getServletContext().getRequestDispatcher(GATEWAY_URL_ADAPTER);
            httpServletRequest.setAttribute("url", originUrl);
            logger.info("old url need to be dispatcher, old url:[{}], new url:[{}]", originUrl, GATEWAY_URL_ADAPTER);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        logger.info("UrlAdapterFilter destroy");
    }
}
