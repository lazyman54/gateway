package com.dafy.base.gateway.service.impl;

import com.dafy.base.gateway.common.util.MapUtils;
import com.dafy.base.gateway.service.InvokeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

/**
 * @author maojinlin
 */
public abstract class AbstractInvokeServiceImpl implements InvokeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInvokeServiceImpl.class);

    static final Map<String, Object> UNSUPPORTED_RETURN_RESULT;

    static final Map<String, Object> SERVICE_NOT_EXIST_RETURN_RESULT;

    private static final int UNSUPPORTED_PROTOCOL_CODE = -1001;

    private static final int SERVICE_NOT_EXIST_CODE = -1002;

    static {
        LOGGER.debug("init default return value.");

        Map<String, Object> unsupportedReturnResult = MapUtils.newHashMap();
        unsupportedReturnResult.put("code", UNSUPPORTED_PROTOCOL_CODE);
        unsupportedReturnResult.put("msg", "unsupported protocol");
        UNSUPPORTED_RETURN_RESULT = Collections.unmodifiableMap(unsupportedReturnResult);

        Map<String, Object> serviceNotExistReturnResult = MapUtils.newHashMap();
        serviceNotExistReturnResult.put("code", SERVICE_NOT_EXIST_CODE);
        serviceNotExistReturnResult.put("msg", "unsupported protocol");
        SERVICE_NOT_EXIST_RETURN_RESULT = Collections.unmodifiableMap(serviceNotExistReturnResult);

    }


}
