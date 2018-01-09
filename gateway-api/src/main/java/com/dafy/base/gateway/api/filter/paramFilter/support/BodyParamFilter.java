package com.dafy.base.gateway.api.filter.paramFilter.support;

import com.alibaba.fastjson.JSON;
import com.dafy.base.gateway.api.filter.paramFilter.IBodyDataFilter;
import com.dafy.base.gateway.common.domain.enums.GatewayParamEnum;
import com.dafy.base.gateway.common.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/14
 */
@Component
@Slf4j
public class BodyParamFilter implements IBodyDataFilter, Closeable {

    private static final ThreadLocal<String> THREAD_LOCAL_ORIGIN_STR = new ThreadLocal<>();
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL_JSON = new ThreadLocal<>();

    @Override
    public String getAppKey(HttpServletRequest request) throws IOException {

        Map<String, Object> map = getParamMap(request);

        return (String) map.get(GatewayParamEnum.APP_KEY.getKey());
    }

    @Override
    public String getUrl(HttpServletRequest request) throws IOException {
        Map<String, Object> map = getParamMap(request);

        return (String) map.get(GatewayParamEnum.URL.getKey());
    }

    @Override
    public String getByParamKey(String paramKey, HttpServletRequest request) throws IOException {
        Map<String, Object> map = getParamMap(request);

        return (String) map.get(paramKey);

    }

    @Override
    public Map<String, Object> getParamMap(HttpServletRequest request) throws IOException {
        Map<String, Object> objectMap = getBodyJsonData(request);
        return objectMap == null ? MapUtils.newHashMap(0) : objectMap;
    }

    @Override
    public String getBodyOriginData(HttpServletRequest request) throws IOException {
        return getBodyStr(request);
    }

    private String getBodyStr(HttpServletRequest request) throws IOException {

        if (StringUtils.isBlank(THREAD_LOCAL_ORIGIN_STR.get())) {

            THREAD_LOCAL_ORIGIN_STR.set(readOriginBodyStr(request));
        }

        return THREAD_LOCAL_ORIGIN_STR.get();
    }

    private Map<String, Object> getBodyJsonData(HttpServletRequest request) throws IOException {

        if (MapUtils.isEmpty(THREAD_LOCAL_JSON.get())) {
            String originStr = getBodyStr(request);

            THREAD_LOCAL_JSON.set(setThreadMap(originStr));
        }

        return THREAD_LOCAL_JSON.get();
    }

    private String readOriginBodyStr(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();

        StringBuilder strBuffer = new StringBuilder();

        reader.lines().forEach((line) -> strBuffer.append(StringUtils.trim(line)));
        return strBuffer.toString();
    }


    private Map<String, Object> setThreadMap(String originStr) {
        try {
            return JSON.parseObject(originStr);
        } catch (Exception e) {
            log.warn("request中的参数无法解析为键值对格式，数据：[{}]", originStr);
            return null;
        }
    }


    @Override
    public void close() throws IOException {
        THREAD_LOCAL_ORIGIN_STR.remove();
        THREAD_LOCAL_JSON.remove();
    }
}
