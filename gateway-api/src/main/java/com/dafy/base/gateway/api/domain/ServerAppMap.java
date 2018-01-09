package com.dafy.base.gateway.api.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/24
 */
@Getter
@Setter
@ToString
public class ServerAppMap {
    private String server;
    private String app;
    private String secretKey;

}
