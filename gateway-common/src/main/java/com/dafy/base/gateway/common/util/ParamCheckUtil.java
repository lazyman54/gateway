package com.dafy.base.gateway.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/6
 */
public class ParamCheckUtil {


    /**
     * 检测上传过来的每个字符串都不为空
     *
     * @param str 传递过来的字符串数组
     * @throws IllegalArgumentException 如果任何一个字符串为空，则抛出异常
     */
    public static void checkNotNullStr(String... str) throws IllegalArgumentException {
        if (str == null || str.length == 0) {
            return;
        }
        for (String s : str) {
            if (StringUtils.isBlank(s)) {
                throw new IllegalArgumentException("不能有为空的参数，请查验");
            }
        }
    }
}
