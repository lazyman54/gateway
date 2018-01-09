package com.dafy.base.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 */
@Service
public class HttpInvokeServiceImpl extends AbstractInvokeServiceImpl {
    @Override
    public TransmitProtocol getProtocol() {
        return TransmitProtocol.HTTP;
    }

    @Override
    public String syncInvoke(Invocation redirectInfo, Map<String, Object> param) {
        return JSON.toJSONString(UNSUPPORTED_RETURN_RESULT);
    }

    @Override
    public String asyncInvoke(Invocation redirectInfo, Map<String, Object> param) {
        return JSON.toJSONString(UNSUPPORTED_RETURN_RESULT);
    }
}
