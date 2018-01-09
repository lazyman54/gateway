package com.dafy.base.gateway.common.domain.constants.errorCode;

/**
 * 签名校验错误码
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/12
 */
public class SignErrorCode extends AbstractErrorCode {

    /**
     * 缺少签名
     */
    public static final String LACK_OF_SIGN = SIGN_ERROR_CODE + "01";

    /**
     * 签名错误
     */
    public static final String SIGN_ERROR = SIGN_ERROR_CODE + "00";

}
