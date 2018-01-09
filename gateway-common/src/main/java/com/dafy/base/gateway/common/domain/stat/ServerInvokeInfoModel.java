package com.dafy.base.gateway.common.domain.stat;

import com.dafy.base.gateway.common.domain.enums.InvokeStateEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务调用信息统计数据
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
@Getter
@Setter
@ToString
@Builder
public class ServerInvokeInfoModel implements Serializable {
    private static final long serialVersionUID = -8240125925080916322L;
    private String appKey;
    private String url;
    private Date startTime;
    private Date endTime;
    private long duration;
    private InvokeStateEnum stateEnum;
    private String exception;
}
