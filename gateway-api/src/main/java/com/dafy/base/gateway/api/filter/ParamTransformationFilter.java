package com.dafy.base.gateway.api.filter;

import com.dafy.base.gateway.api.filter.paramFilter.IBodyDataFilter;
import com.dafy.base.gateway.api.filter.paramFilter.IParamFilter;
import com.dafy.base.gateway.api.interceptor.HttpRequestWrapper;
import com.dafy.base.gateway.common.domain.enums.GatewayParamEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/19
 */
@Component
@Order(25)
public class ParamTransformationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ParamTransformationFilter.class);

    private final IParamFilter paramFilter;

    private ParamTransformRuleHolder holder;

    public ParamTransformationFilter(IParamFilter paramFilter) {
        this.paramFilter = paramFilter;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        holder = ParamTransformRuleHolder.getInstance();
        logger.info("ParamTransformationFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String newUrl = StringUtils.defaultIfBlank((String) request.getAttribute("url"), paramFilter.getUrl((HttpServletRequest) request));
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper((HttpServletRequest) request, StringUtils.trim(newUrl));
        String appKey = paramFilter.getAppKey((HttpServletRequest) request);
        requestWrapper.addParam(GatewayParamEnum.APP_KEY.getKey(), appKey);
        logger.info("invoke http url: [{}], appKey:[{}]", newUrl, appKey);


        String contentType = holder.getContentType(request);

        if (holder.isContentTypeNeedSpecialHandle(contentType)) {

            Map<String, Object> jsonMap = ((IBodyDataFilter) paramFilter).getParamMap((HttpServletRequest) request);

            jsonMap.forEach(requestWrapper::addParam);
            requestWrapper.addParam(GatewayParamEnum.ORIGIN_DATA.getKey(), ((IBodyDataFilter) paramFilter).getBodyOriginData((HttpServletRequest) request));
        }
        ((Closeable) paramFilter).close();

        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
        logger.info("ParamTransformationFilter destroy");
    }
}
