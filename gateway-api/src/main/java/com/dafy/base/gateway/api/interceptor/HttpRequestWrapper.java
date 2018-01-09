package com.dafy.base.gateway.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.dafy.base.gateway.common.domain.enums.GatewayParamEnum;
import com.dafy.base.gateway.common.util.CollectionUtil;
import com.dafy.base.gateway.common.util.MapUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/10
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, List<String>> paramMaps;

    public HttpRequestWrapper(HttpServletRequest request, String url) {
        super(request);
        this.paramMaps = MapUtils.getHashMap(GatewayParamEnum.URL.getKey(), Collections.singletonList(url));

    }

   /* private static final ImmutableSet<String> GATEWAY_PARAMS;

    static {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        for (GatewayParamEnum gatewayParamEnum : GatewayParamEnum.values()) {
            builder.add(gatewayParamEnum.getKey());
        }
        GATEWAY_PARAMS = builder.build();
    }*/

    /**
     * @see #addParam(String, Object)
     */
    @Deprecated
    public void setAppKey(String appKey) {
        addParam(GatewayParamEnum.APP_KEY.getKey(), appKey);
    }

    public void addParam(String paramKey, Object paramValue) {

        List<String> values = paramMaps.getOrDefault(paramKey, new ArrayList<>());

        try {
            /*客户端如果将appKey等信息放在body中，这里要防止添加两次的错误*/
            if (GatewayParamEnum.contain(paramKey) && values.size() > 0) {
                return;
            }
            values.add((String) paramValue);
        } catch (Exception e) {
            values.add(JSON.toJSONString(paramValue));
        }

        paramMaps.put(paramKey, values);
    }

    @Override
    public String getParameter(String name) {
        String[] values = this.getParameterValues(name);

        if (values == null || values.length == 0) {
            return null;
        }

        return values[0];
    }


    @Override
    public String[] getParameterValues(String name) {

        String[] values = super.getParameterValues(name);

        if (values == null || values.length == 0) {


            List<String> valueList = paramMaps.get(name);
            if (CollectionUtil.isEmpty(valueList)) {
                return null;
            }
            return valueList.toArray(new String[valueList.size()]);
        }
        return values;
    }


    @Override
    public Map<String, String[]> getParameterMap() {

        Map<String, String[]> resultMap = getParameterMap0();

        super.getParameterMap().forEach(resultMap::put);

        return resultMap;
    }

    private Map<String, String[]> getParameterMap0() {
        Map<String, String[]> tempMap = MapUtils.newHashMap();

        paramMaps.forEach((key, value) -> tempMap.put(key, value.toArray(new String[value.size()])));

        return tempMap;

    }

    @Override
    public String toString() {
        return "HttpRequestWrapper{" +
                "paramMaps=" + paramMaps +
                "} " + super.toString();
    }
}
