package com.dafy.base.gateway.common.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/30
 */
@Getter
@Setter
@ToString
public class InvokeRecordDto implements Serializable {
    private static final long serialVersionUID = 810107916013469408L;
    private String id;

    /**
     * 经过ob服务之后产生的事件id
     */
    private String tid;

    /**
     * 接口耗时，单位毫秒
     */
    private int duration;

    /**
     * 调用状态，SUCCESS/FAIL/EXCEPTION
     */
    private String invokeStatus;

    /**
     * appKey
     */
    private String appKey;

    /**
     * 调用的url
     */
    private String httpUrl;

    /**
     * 调用开始时间
     */
    private long beginTime;

    /**
     * 调用结束时间
     */
    private long endTime;

    /**
     * 异常，此时InvokeStatus为EXCEPTION
     */
    private String exception;

    /**
     * 当前时间
     */
    private Date createTime;

    /**
     * 操作者机器的ip
     */
    private String operatorIp;

    /**
     * 事件名
     */
    private String event;

    /**
     * app名
     */
    private String bizApp;

    /**
     * 当前处理状态
     */
    private Integer handlerState;


}
