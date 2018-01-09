package com.dafy.base.gateway.data.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/29
 */
@Document(collection = "InvokeRecord")
@CompoundIndexes(@CompoundIndex(name = "IDX_APP_URL", def = "{'appKey': 1, 'url': 1}"))
@Getter
@Setter
@ToString
public class InvokerRecordMdo implements Serializable {

    private static final long serialVersionUID = 842659959285257865L;

    @Id
    private String id;

    /**
     * 经过ob服务之后产生的事件id
     */
    private String tid;

    /**
     * 接口耗时，单位毫秒
     */
    @Indexed(name = "DURATION")
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

}
