package com.dafy.base.gateway.manage.advice;

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

    private static final String INVOKE_TIME_OUT = "times of the providers";


    @ExceptionHandler(value = DafyBaseException.class)
    @ResponseBody
    public ResponseModel handlerDafyException(DafyBaseException exception) {
        logger.error("exception: ", exception);

        ResponseModel.Builder builder = ResponseModel.newBuilder();
        builder.code(exception.getCode()).message(exception.getMessage());


        return builder.build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseModel serverNotExistException(RuntimeException exception) {
        logger.error("exception: ", exception);
        ResponseModel.Builder builder = ResponseModel.newBuilder();
        if (DafyBaseException.class.isInstance(exception.getCause())) {
            return handlerDafyException((DafyBaseException) exception.getCause());
        } else if (RpcException.class.isInstance(exception.getCause())) {
            if (StringUtils.contains(exception.getMessage(), NO_RPOVIDER)) {
                builder.code(ServerErrorCode.SERVER_BUSY).message("服务调用超时，请检查后重试");
            } else if (StringUtils.contains(exception.getMessage(), INVOKE_TIME_OUT)) {
                builder.code(ServerErrorCode.SERVER_NOT_AVAILABLE).message("没有找到服务提供者，请检查后重试");
            } else {
                builder.code(SystemErrorCode.UNKNOW_ERROR).message("rpc provider exception");
            }
        } else if (exception instanceof IllegalStateException) {
            if (StringUtils.contains(exception.getMessage(), NO_RPOVIDER)) {
                builder.code(ServerErrorCode.SERVER_BUSY).message("服务调用超时，请检查后重试");
            } else {
                builder.code(SystemErrorCode.UNKNOW_ERROR).message("rpc provider exception");
            }
        } else {
            builder.code(SystemErrorCode.UNKNOW_ERROR).message("server unknown exception");
        }

        return builder.build();
    }
}
