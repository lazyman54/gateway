package com.dafy.base.gateway.common.domain.constants.errorCode;

/**
 * 登录权限问题错误码
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/12
 */
public class AuthErrorCode extends AbstractErrorCode {

    /**
     * 用户未登录
     */
    public static final String NOT_LOGIN = AUTH_ERROR_CODE + "01";

    /**
     * 用户无权限访问当前资源
     */
    public static final String NO_AUTH = AUTH_ERROR_CODE + "02";

    /**
     * 权限过期或者登录过期
     */
    public static final String AUTH_EXPIRED = AUTH_ERROR_CODE + "03";

}
