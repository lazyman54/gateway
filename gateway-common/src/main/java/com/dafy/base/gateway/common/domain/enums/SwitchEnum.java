package com.dafy.base.gateway.common.domain.enums;

import com.google.common.collect.ImmutableMap;

/**
 * 开关类的枚举，开启用1，关闭用0
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
public enum SwitchEnum {
    OFF(0, "关闭"),
    ON(1, "开启"),
    HALF(2, "半开启");

    private int key;
    private String value;

    SwitchEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private static final ImmutableMap<Integer, SwitchEnum> cache;

    static {

        ImmutableMap.Builder<Integer, SwitchEnum> builder = ImmutableMap.builder();

        for (SwitchEnum switchEnum : SwitchEnum.values()) {
            builder.put(switchEnum.getKey(), switchEnum);
        }
        cache = builder.build();
    }

    public static SwitchEnum fromKey(Integer key) {
        return cache.get(key);
    }


    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
