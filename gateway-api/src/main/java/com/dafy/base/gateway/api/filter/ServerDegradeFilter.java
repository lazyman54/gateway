package com.dafy.base.gateway.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/10
 */
@Component
@Order(60)
public class ServerDegradeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ServerDegradeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("server degrade filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("处理服务降级拦截");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("server degrade filter destroy");
    }
}
