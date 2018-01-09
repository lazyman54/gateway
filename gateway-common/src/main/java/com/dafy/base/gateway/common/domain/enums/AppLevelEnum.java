package com.dafy.base.gateway.common.domain.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * APP 的级别。
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/9/27
 */
public enum AppLevelEnum {

    /*优先级高*/
    HIGH,
    /*优先级中*/
    MIDDLE,
    /*优先级低*/
    LOW;

    public static Collection<String> higherLevel(AppLevelEnum appLevelEnum) {
        switch (appLevelEnum) {
            case LOW:
                return Arrays.asList(HIGH.toString(), MIDDLE.toString(), LOW.toString());
            case MIDDLE:
                return Arrays.asList(HIGH.toString(), MIDDLE.toString());
            case HIGH:
                return Collections.singletonList(HIGH.toString());
            default:
                return new ArrayList<>(0);
        }
    }
}
