package com.dafy.base.gateway.api.filter;

import com.dafy.base.gateway.api.configuration.ProjectProperties;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p>限制访问的频率</p>
 * <p>通过Semaphore信号量来限制不同url的访问速率，同时也限制当前机器的访问速率</p>
 * <p>限制当前机器的访问速率有两种可选方案，其一默认所有的机器都是一样的，最大处理速度是一样的，其二，默认所有的机器都是不一样的，最大处理速率也是不一样的</p>
 * <p>当此机器的并发数达到某个预警值之后，将会开启服务降级业务，通常预警值为机器最大容量的百分之七十</p>
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/12/14
 */
@Slf4j
public class TrafficRateLimiter {

    private volatile boolean hasInit;

    private final Map<String, Map<String, Semaphore>> urlLimitMap;

    private Semaphore machineSemaphore;

    private float overloadFactor;


    public TrafficRateLimiter() {
        this.hasInit = false;
        this.urlLimitMap = new HashMap<>();
    }

    void init(Collection<TransmitMapDTO> dtos, ProjectProperties properties) {

        if (hasInit) {
            log.warn("限流器已经初始化过了，无需再次初始化");
            return;
        }

        this.overloadFactor = properties.getOverloadFactor();
        this.machineSemaphore = new Semaphore(properties.getMaxTps());

        /*for (TransmitMapDTO dto : dtos) {
            String appKey = dto.getAppKey();
            String url = dto.getHttpUrl();
            Integer tps = dto.getTps();
            Map<String, Semaphore> semaphoreMap = urlLimitMap.get(appKey);
            if (semaphoreMap == null) {
                semaphoreMap = new HashMap<>();
                urlLimitMap.putIfAbsent(appKey, semaphoreMap);
            }

            if (tps == null || tps <= 0) {
                semaphoreMap.putIfAbsent(url, new Semaphore(Integer.MAX_VALUE));
            } else {
                semaphoreMap.putIfAbsent(url, new Semaphore(tps));
            }
        }*/

        this.hasInit = true;
    }

    /*boolean applyToPass(String appKey, String httpUrl) {
        return machineSemaphore.tryAcquire();
    }*/
   /* boolean applyToPass() {
        return machineSemaphore.tryAcquire();
    }*/

    /*boolean applyToPass(String appKey, String httpUrl, long waitTime) {
        try {
            return machineSemaphore.tryAcquire(waitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("apply to pass was interrupt while waiting permission, e:{}", e);
            return false;
        }
    }*/

    boolean applyToPass(long waitTime) {
        try {
            return machineSemaphore.tryAcquire(waitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("apply to pass was interrupt while waiting permission, e:{}", e);
            return false;
        }
    }

    /*void passComplete(String appKey, String httpUrl) {
        machineSemaphore.release();
    }*/

    void passComplete() {
        machineSemaphore.release();
    }


}
