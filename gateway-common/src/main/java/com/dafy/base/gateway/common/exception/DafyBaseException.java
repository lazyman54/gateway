package com.dafy.base.gateway.common.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * 通用异常父类
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/6/30
 */
@ToString(callSuper = true)
@Getter
public class DafyBaseException extends Exception {

    private static final long serialVersionUID = 7336395359592187105L;

    protected String code;

    public DafyBaseException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public DafyBaseException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }
}
