package com.dafy.base.gateway.api.service;

import com.dafy.base.gateway.api.domain.GatewayRpcParam;
import com.dafy.base.gateway.common.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/12
 */
@Service
public class ParamHandleServer {

    public GatewayRpcParam handleParam(String url, String appKey, String version, HttpServletRequest request) {

        GatewayRpcParam.Builder builder = GatewayRpcParam.newBuilder();
        builder.url(url).appKey(appKey).version(version);
        Map<String, String[]> parameterMap = request.getParameterMap();

        Map<String, Object> paramMap = MapUtils.newHashMap(parameterMap.size() * 2);

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            paramMap.put(entry.getKey(), entry.getValue()[0]);
        }
        paramMap.remove("appKey");
        paramMap.remove("url");
        return builder.data(paramMap).build();
    }

}
