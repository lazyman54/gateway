package com.dafy.base.gateway.common.domain.enums;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
public enum TransmitProtocol {
    UNKNOWN(),

    /*
        支持dubbo协议服务的转发
     */
    DUBBO(),

    /*
        支持http协议服务转发
     */
    HTTP(),

    /*
        支持web service协议服务转发
     */
    WEBSERVICE();

    TransmitProtocol() {
    }
}
