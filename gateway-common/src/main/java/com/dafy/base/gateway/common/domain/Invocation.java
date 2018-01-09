package com.dafy.base.gateway.common.domain;

import com.dafy.base.gateway.common.domain.enums.ReturnTypeEnum;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
public interface Invocation {

    String getMethodName();

    List<ParamDefinition> getParameterDefinition();

    String getInvokeClass();

    String getVersion();

    ReturnTypeEnum getReturnType();

    int getParameterCount();

    String getRegistryCenter();

    Integer getTimeout();

    Integer getTryTime();

}
