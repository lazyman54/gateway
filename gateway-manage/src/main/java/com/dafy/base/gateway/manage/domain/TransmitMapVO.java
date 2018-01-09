package com.dafy.base.gateway.manage.domain;

import com.dafy.base.gateway.common.annotation.IgnoreFieldOnCopy;
import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/10
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TransmitMapVO implements Serializable {

    private static final long serialVersionUID = 4910731104516425844L;
    private String id;
    private String appKey;
    private String httpUrl;
    private TransmitProtocol protocol;
    private String level;
    private String sync;
    private String urlAdapter;

    @IgnoreFieldOnCopy
    private String invokeClassName;
    @IgnoreFieldOnCopy
    private String methodName;
    @IgnoreFieldOnCopy
    private List<ParamDefinitionVo> paramDefinitions;
    @IgnoreFieldOnCopy
    private String version;
    @IgnoreFieldOnCopy
    private String registryCenter;
    /**
     * 接口超时时间
     */
    @IgnoreFieldOnCopy
    private Integer timeout;

    /**
     * 失败重试次数
     */
    @IgnoreFieldOnCopy
    private Integer tryTime;

    /**
     * 单台机器最高的tps
     */
    private Integer tps;

    /**
     * 服务开关
     */
    private String enableSwitch;

}
