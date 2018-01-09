package com.dafy.base.gateway.data.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
@Document(collection = "Config")
@CompoundIndexes(@CompoundIndex(name = "UDX_APP_URL", unique = true, def = "{'appKey': 1, 'url': 1}"))
@Getter
@Setter
@ToString
public class ConfigMdo {
    @Id
    private String id;

    private String appKey;

    private String url;

    /**
     * url接口是否打开，{@link com.dafy.base.gateway.common.domain.enums.SwitchEnum}
     */
    private int enableSwitch;

    /**
     * url允许的最大tps，此数值应当结合api部署的机器数来确定如何分配
     */
    private int maxTps;

    /**
     * 接口的优先级，用于服务降级
     */
    private int level;

    private Date createTime;

    private Date updTime;

    private String operator;


}
