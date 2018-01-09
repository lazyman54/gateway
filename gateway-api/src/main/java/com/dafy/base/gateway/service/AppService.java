package com.dafy.base.gateway.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.data.api.IAppKeyRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/10/16
 */
@Service
@Slf4j
public class AppService {

    @Reference(version = "1.0.0")
    private IAppKeyRpc iAppKeyRpc;

    public List<AppServerDTO> listAll() {
        log.info("list all app server");
        return iAppKeyRpc.listAll();
    }

    public AppServerDTO getByAppKey(String appKey) {
        // TODO: 2017/10/16 maojinlin 这里要加本地缓存，毕竟数据更新频率很低，典型的大量读的场景
        return iAppKeyRpc.getServerName(appKey);
    }

}
