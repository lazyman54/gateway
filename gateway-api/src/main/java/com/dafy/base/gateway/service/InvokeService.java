package com.dafy.base.gateway.service;

import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;

import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 */
public interface InvokeService {

    TransmitProtocol getProtocol();

    Object syncInvoke(Invocation invocation, Map<String, Object> param);

    Object asyncInvoke(Invocation invocation, Map<String, Object> param);
}
