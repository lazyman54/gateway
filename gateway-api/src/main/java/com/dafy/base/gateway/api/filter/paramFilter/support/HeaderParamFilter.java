package com.dafy.base.gateway.api.filter.paramFilter.support;

import com.dafy.base.gateway.api.filter.paramFilter.IBodyDataFilter;
import com.dafy.base.gateway.api.filter.paramFilter.IParamFilter;
import com.dafy.base.gateway.common.domain.enums.GatewayParamEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/14
 */
@Component("paramFilter")
public class HeaderParamFilter implements IBodyDataFilter, Closeable {

    private final IParamFilter nextFilter;

    @Autowired
    public HeaderParamFilter(QueryParamFilter nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override
    public String getAppKey(HttpServletRequest request) throws IOException {

        String value = request.getHeader(GatewayParamEnum.APP_KEY.getKey());

        return StringUtils.isNotBlank(value) ? value.trim() : nextFilter.getAppKey(request);
    }

    @Override
    public String getUrl(HttpServletRequest request) throws IOException {
        String value = request.getHeader(GatewayParamEnum.URL.getKey());

        return StringUtils.isNotBlank(value) ? value.trim() : nextFilter.getUrl(request);
    }

    @Override
    public String getByParamKey(String paramKey, HttpServletRequest request) throws IOException {
        String value = request.getHeader(paramKey);
        return StringUtils.isNotBlank(value) ? value.trim() : nextFilter.getByParamKey(paramKey, request);
    }

    @Override
    public Map<String, Object> getParamMap(HttpServletRequest request) throws IOException {
        return ((IBodyDataFilter) nextFilter).getParamMap(request);
    }

    @Override
    public String getBodyOriginData(HttpServletRequest request) throws IOException {
        return ((IBodyDataFilter) nextFilter).getBodyOriginData(request);
    }

    @Override
    public void close() throws IOException {
        ((Closeable) nextFilter).close();
    }
}
