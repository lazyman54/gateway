package com.dafy.base.gateway.manage.controller;

import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.manage.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/5
 */
@Slf4j
@RestController
@RequestMapping("/base/config")
public class ConfigController {

    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/list")
    public ResponseModel listConfig(String appKey, String url) throws Exception {

        log.info("list config, appKey:[{}], url:[{}]", appKey, url);

        return ResponseModel.newBuilder().data(configService.getByAppKeyAndUrl(appKey, url)).build();
    }

    /**
     * 更新配置项
     *
     * @param appKey appKey
     * @param url    url地址
     * @param key    开关项名称
     * @param value  值
     */
    @PostMapping("/upd")
    public ResponseModel upd(String appKey, String url, String key, String value, String type) {
        return ResponseModel.newBuilder().build();
    }
}
