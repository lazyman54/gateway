package com.dafy.base.gateway.api.filter;

import com.dafy.base.gateway.common.util.MapUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/7
 */
public class TrafficMapHolder {

    private final Map<String, Integer> urlRequestTimeMap = MapUtils.newConcurrentHashMap();

    private final AtomicInteger urlTotalCount = new AtomicInteger(0);

    TrafficMapHolder() {
    }

    int getTotalCount() {
        return urlTotalCount.get();
    }

    int getUrlCount(String url) {
        return urlRequestTimeMap.getOrDefault(url, 0);
    }

    void addUrlCount(String url) {
        addUrlCount(url, 1);
    }

    void addUrlCount(String url, int count) {

        urlTotalCount.incrementAndGet();
        urlRequestTimeMap.merge(url, count, (oldValue, newValue) -> oldValue + newValue);

    }

    void decUrlCount(String url) {
        urlTotalCount.decrementAndGet();
        urlRequestTimeMap.merge(url, 0, (oldValue, newValue) -> --oldValue);

    }

    /*public static void main(String[] args) {
        TrafficMapHolder urlRequestTimeMapHolder = new TrafficMapHolder();

        System.out.println(urlRequestTimeMapHolder.getUrlCount("abc"));
        urlRequestTimeMapHolder.addUrlCount("abc");
        System.out.println(urlRequestTimeMapHolder.getUrlCount("abc"));
        urlRequestTimeMapHolder.addUrlCount("abc");
        System.out.println(urlRequestTimeMapHolder.getUrlCount("abc"));

    }*/

}
