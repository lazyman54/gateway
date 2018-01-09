package com.dafy.base.gateway.api.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/14
 */
@ConfigurationProperties(prefix = "project.data")
@Getter
@Setter
@ToString(callSuper = true)
public class ProjectProperties {
    /**
     * 服务器最大能任一时刻能处理的请求数
     */
    private int maxTps;
    /**
     * 负载过高的负载因子，默认请使用0.75
     */
    private float overloadFactor;

}
