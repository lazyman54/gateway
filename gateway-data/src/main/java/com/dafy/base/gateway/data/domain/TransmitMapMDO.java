package com.dafy.base.gateway.data.domain;

import com.dafy.base.gateway.common.annotation.IgnoreFieldOnCopy;
import com.dafy.base.gateway.common.domain.ParamDefinition;
import com.dafy.base.gateway.common.domain.enums.ReturnTypeEnum;
import com.dafy.base.gateway.common.domain.enums.SwitchEnum;
import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
@Document(collection = "TransmitMap")
@CompoundIndexes(@CompoundIndex(unique = true, name = "UDX_APP_URL", def = "{'appKey': 1, 'httpUrl': 1}"))
@Getter
@Setter
@ToString
public class TransmitMapMDO implements Serializable {

    private static final long serialVersionUID = 5642391989469154069L;
    @Id
    private String id;
    /**
     * app的唯一标识
     */
    private String appKey;

    /**
     * 映射的http请求url
     */
    private String httpUrl;

    /**
     * 接口优先级别
     */
    private String level;

    /**
     * 服务所使用的协议
     */
    private TransmitProtocol protocol;

    /**
     * 是否同步调用，默认是
     */
    private Boolean sync;

    /**
     * 是否是url带参数适配
     */
    private Boolean urlAdapter;
    /**
     * 服务方法名
     */
    @IgnoreFieldOnCopy
    private String methodName;

    /**
     * 参数数量
     */
    @IgnoreFieldOnCopy
    private Integer parameterCount;
    /**
     * 返回类型
     */
    @IgnoreFieldOnCopy
    private ReturnTypeEnum returnTypeEnum;

    /**
     * 接口全路径名
     */
    @IgnoreFieldOnCopy
    private String invokeClass;
    /**
     * 参数列表
     */
    @IgnoreFieldOnCopy
    private List<ParamDefinition> paramDefinitions;

    /**
     * 服务接口版本
     */
    @IgnoreFieldOnCopy
    private String version;

    /**
     * 注册中心地址，默认使用网关所在注册中心的地址
     */
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

    private SwitchEnum enableSwitch;

    private Date createTime;

    private Date updTime;

    private String operator;
}
