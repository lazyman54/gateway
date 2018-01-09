package com.dafy.base.gateway.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/6
 */
public class RedisKeyUtil {

    public static String formatFromOriginUrl(String originUrl) {

        String str = originUrl;

        if (StringUtils.startsWith(originUrl, "/")) {
            str = StringUtils.substring(originUrl, 1);
        }

        return StringUtils.replace(str, "/", ".");
    }

    public static String formatToOriginUrl(String formattedUrl) {
        return StringUtils.replace(formattedUrl, ".", "/");
    }


    public static void main(String[] args) {
        String ss = "/base/gateway";

        System.out.println(formatFromOriginUrl(ss));

    }
}
