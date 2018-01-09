package com.dafy.base.gateway.common.exception;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/6
 */
public class ServerNotExistException extends DafyBaseException {
    private static final long serialVersionUID = -4001512235384544326L;

    public ServerNotExistException(String code, String msg) {
        super(code, msg);
    }

    public ServerNotExistException(String code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
