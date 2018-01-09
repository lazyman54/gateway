package com.dafy.base.gateway.api.advice;

import com.alibaba.dubbo.remoting.TimeoutException;
import com.alibaba.dubbo.rpc.RpcException;
import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.common.domain.constants.errorCode.ServerErrorCode;
import com.dafy.base.gateway.common.domain.constants.errorCode.SystemErrorCode;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/4
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    private static final String NO_RPOVIDER = "No provider available for the service";

    private static final String INVOKE_TIME_OUT = "Invoke remote method timeout";
    //private static final String INVOKE_TIME_OUT = "times of the providers";

    /**
     * 服务下线了。
     */
    private static final String PROVIDER_OFF_LINE = "Please check registry access list";

    public static final ThreadLocal<String> ERROR_MESSAGE_THREAD_LOCAL = new ThreadLocal<>();


    @ExceptionHandler(value = DafyBaseException.class)
    @ResponseBody
    public ResponseModel handlerDafyException(DafyBaseException exception) {
        logger.error("exception: ", exception);

        ResponseModel.Builder builder = ResponseModel.newBuilder();
        builder.code(exception.getCode()).message(exception.getMessage());

        ERROR_MESSAGE_THREAD_LOCAL.set(exception.getMessage());
        return builder.build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseModel serverNotExistException(RuntimeException exception) {
        logger.error("exception: ", exception);
        ResponseModel.Builder builder = ResponseModel.newBuilder();
        if (DafyBaseException.class.isInstance(exception.getCause())) {
            return handlerDafyException((DafyBaseException) exception.getCause());
        } else {
            if (StringUtils.contains(exception.getMessage(), PROVIDER_OFF_LINE)) {
                builder.code(ServerErrorCode.SERVER_NOT_ONLINE).message("服务已经下线了，请检查");
            } else if (StringUtils.contains(exception.getMessage(), INVOKE_TIME_OUT)) {
                builder.code(ServerErrorCode.SERVER_BUSY).message("服务调用超时，请检查后重试");
            } else if (StringUtils.contains(exception.getMessage(), NO_RPOVIDER)) {
                builder.code(ServerErrorCode.SERVER_NOT_AVAILABLE).message("当前服务不在线，请检查当前相关服务状态后重试");
            } else {
                if (RpcException.class.isInstance(exception.getCause())) {
                    builder.code(SystemErrorCode.UNKNOW_ERROR).message("rpc调用异常");
                } else if (NullPointerException.class.isInstance(exception.getCause())) {
                    builder.code(SystemErrorCode.UNKNOW_ERROR).message("开发人员粗心了，报此错误，请携带棍棒前去面谈开发");
                } else if (TimeoutException.class.isInstance(exception.getCause())) {
                    builder.code(ServerErrorCode.SERVER_BUSY).message("服务提供者返回超时，请检查后重试");
                } else if (ClassCastException.class.isInstance(exception.getCause())) {
                    builder.code(ServerErrorCode.SERVER_CONFIG_ERROR).message("请检查服务配置信息");
                } else {
                    builder.code(SystemErrorCode.UNKNOW_ERROR).message("服务器未知异常");
                }
            }
        }
        ERROR_MESSAGE_THREAD_LOCAL.set(exception.getMessage());
        return builder.build();
    }

}
