package com.dafy.base.gateway.manage.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dafy.base.gateway.common.domain.dto.ConfigDto;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.data.api.IConfigRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/5
 */
@Slf4j
@Service
public class ConfigService {

    @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:20880")
    private IConfigRpc configRpc;


    public ConfigDto getByAppKeyAndUrl(String appKey, String url) throws DafyBaseException {
        ConfigDto configDto = configRpc.getConfig(appKey, url);

        if (configDto == null) {
            log.warn("根据appKey:[{}],url:[{}] 查找不到对应的配置类", appKey, url);
            throw new DafyBaseException("300", "查找不到对应的配置类");
        }
        return configDto;
    }

}
