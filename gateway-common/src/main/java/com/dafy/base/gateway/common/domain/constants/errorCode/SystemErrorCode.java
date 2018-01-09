package com.dafy.base.gateway.common.domain.constants.errorCode;

/**
 * 系统错误码
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/12
 */
public class SystemErrorCode extends AbstractErrorCode {

    /**
     * 系统未知错误,当难以定义明确错误码或者无需定义明确错误码时，可以采用此错误码
     */
    public static final String UNKNOW_ERROR = SYSTEM_ERROR_CODE + "00";

    /**
     * 网络错误
     */
    public static final String NETWORK_ERROR = SYSTEM_ERROR_CODE + "01";

    /**
     * 机器繁忙，将会拒绝服务
     */
    public static final String MACHINE_BUSY = SYSTEM_ERROR_CODE + "03";

}
