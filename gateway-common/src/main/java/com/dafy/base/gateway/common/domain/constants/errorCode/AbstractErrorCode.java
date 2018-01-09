package com.dafy.base.gateway.common.domain.constants.errorCode;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
public abstract class AbstractErrorCode implements IErrorCode {
    /**
     * 参数错误基准错误码
     */
    static final String PARAM_ERROR_CODE = "1000";
    /**
     * 系统错误基准错误码
     */
    static final String SYSTEM_ERROR_CODE = "2000";
    /**
     * 服务错误基准错误码
     */
    static final String SERVER_ERROR_CODE = "3000";
    /**
     * 权限问题基准错误码
     */
    static final String AUTH_ERROR_CODE = "4000";
    /**
     * 签名问题基准错误码
     */
    static final String SIGN_ERROR_CODE = "5000";
}
