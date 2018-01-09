package com.dafy.base.gateway.api.service;

import com.dafy.base.gateway.common.domain.dto.ConfigDto;
import com.dafy.base.gateway.common.util.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/5
 */
@Service
@Slf4j
public class ConfigService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public ConfigService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public ConfigDto getConfig(String appKey, String url) {
        log.info("get config, appKey:[{}], url:[{}]", appKey, url);

        String redisKey = RedisKey.getServerSwitchKey(appKey);

        BoundHashOperations<Object, String, Object> hashOps = redisTemplate.boundHashOps(redisKey);

        ConfigDto configDto = (ConfigDto) hashOps.get(url);

        return new ConfigDto();
    }

}
