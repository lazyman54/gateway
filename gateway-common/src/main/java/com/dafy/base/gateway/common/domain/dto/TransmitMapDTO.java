package com.dafy.base.gateway.common.domain.dto;

import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.enums.SwitchEnum;
import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
@ToString
@Setter
@Getter
public class TransmitMapDTO implements Serializable {
    private static final long serialVersionUID = 199106627823293122L;

    private String id;

    private String appKey;

    private String httpUrl;

    private TransmitProtocol protocol;

    private String level;

    private Boolean sync;

    private Boolean urlAdapter;

    /**
     * 接口允许的最大的tps，-1为不限制
     */
    private Integer tps;

    private SwitchEnum enableSwitch;

    private Invocation invocation;

}
