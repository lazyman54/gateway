package com.dafy.base.gateway.common.domain;

import com.dafy.base.gateway.common.domain.enums.ReturnTypeEnum;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
@Setter
@ToString
@NoArgsConstructor
public class RpcInvocation implements Invocation, Serializable {

    private static final long serialVersionUID = -1878100305682940375L;

    private String methodName;

    private List<ParamDefinition> paramDefinitions;

    private int parameterCount;

    private ReturnTypeEnum returnTypeEnum;

    private String invokeClass;

    private String version;

    private String registryCenter;

    private Integer timeout;

    private Integer tryTime;

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public List<ParamDefinition> getParameterDefinition() {
        return paramDefinitions;
    }

    @Override
    public String getInvokeClass() {
        return invokeClass;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public ReturnTypeEnum getReturnType() {
        return returnTypeEnum;
    }

    @Override
    public int getParameterCount() {
        return parameterCount;
    }

    @Override
    public String getRegistryCenter() {
        return registryCenter;
    }

    @Override
    public Integer getTimeout() {
        return timeout;
    }

    @Override
    public Integer getTryTime() {
        return tryTime;
    }
}
