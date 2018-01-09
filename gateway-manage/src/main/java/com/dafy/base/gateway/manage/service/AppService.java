package com.dafy.base.gateway.manage.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.data.api.IAppKeyRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/4
 */
@Service
@Slf4j
public class AppService {

    @Reference(version = "1.0.0")
    private IAppKeyRpc appKeyRpc;

    public List<AppServerDTO> listAll() {
        return appKeyRpc.listAll();
    }

    public AppServerDTO getDetail(String id) {
        return appKeyRpc.get(id);
    }

    public int add(AppServerDTO appServerDTO) {
        return appKeyRpc.add(appServerDTO);
    }

    public int upd(AppServerDTO appServerDTO) {
        return appKeyRpc.add(appServerDTO);
    }

    public int del(String id) {
        return appKeyRpc.del(id);
    }

}
