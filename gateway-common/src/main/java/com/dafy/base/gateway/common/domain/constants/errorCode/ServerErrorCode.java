package com.dafy.base.gateway.common.domain.constants.errorCode;

/**
 * 服务错误码
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/12
 */
public class ServerErrorCode extends AbstractErrorCode {

    /**
     * 服务繁忙
     */
    public static final String SERVER_BUSY = SERVER_ERROR_CODE + "01";

    /**
     * 服务不可用
     */
    public static final String SERVER_NOT_AVAILABLE = SERVER_ERROR_CODE + "02";

    /**
     * 服务不在线，通常指网关服务器本身错误
     */
    public static final String SERVER_NOT_ONLINE = SERVER_ERROR_CODE + "03";

    /**
     * 服务无法识别
     */
    public static final String SERVER_NOT_RECOGNIZE = SERVER_ERROR_CODE + "04";

    /**
     * 服务配置错误
     */
    public static final String SERVER_CONFIG_ERROR = SERVER_ERROR_CODE + "05";
}
