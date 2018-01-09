package com.dafy.base.gateway.common.domain.enums;

import com.google.common.collect.ImmutableMap;

/**
 * 网关系统的核心参数枚举
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
public enum GatewayParamEnum {
    URL("url", "实际请求的url"),
    APP_KEY("appKey", "app的标识，每个app唯一"),
    ORIGIN_DATA("originData", "原始数据，针对contentType为非表单键值对的情况"),
    SECRET_KEY("secretKey", "为每个应用分配的加密秘钥");

    private String key;
    private String desc;

    GatewayParamEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private static final ImmutableMap<String, GatewayParamEnum> cache;

    static {

        ImmutableMap.Builder<String, GatewayParamEnum> builder = ImmutableMap.builder();

        for (GatewayParamEnum gatewayParamEnum : GatewayParamEnum.values()) {
            builder.put(gatewayParamEnum.getKey(), gatewayParamEnum);
        }
        cache = builder.build();
    }

    public static GatewayParamEnum fromKey(String key) {
        return cache.get(key);
    }


    public static boolean contain(String key) {
        return fromKey(key) != null;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
