package com.dafy.base.gateway.common.domain.enums;

import com.google.common.collect.ImmutableMap;

/**
 * 调用状态的表示法枚举
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
public enum InvokeStateEnum {

    SUCCESS(1, "成功"),
    FAIL(0, "失败"),
    EXCEPTION(2, "异常");


    private int state;

    private String desc;

    InvokeStateEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    private static final ImmutableMap<Integer, InvokeStateEnum> cache;

    static {

        ImmutableMap.Builder<Integer, InvokeStateEnum> builder = ImmutableMap.builder();

        for (InvokeStateEnum invokeStateEnum : InvokeStateEnum.values()) {
            builder.put(invokeStateEnum.getState(), invokeStateEnum);
        }
        cache = builder.build();
    }

    public static InvokeStateEnum fromKey(Integer key) {
        return cache.get(key);
    }


    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
