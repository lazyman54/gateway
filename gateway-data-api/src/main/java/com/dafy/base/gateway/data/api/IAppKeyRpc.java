package com.dafy.base.gateway.data.api;

import com.dafy.base.gateway.common.domain.dto.AppServerDTO;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
public interface IAppKeyRpc {

    /**
     * 通过服务的名称（host）获取应用的appKey,服务名称如：http://sevend.dafy.com中的sevend.dafy.com
     *
     * @param serverName 服务的名称
     * @return 返回appKey
     */
    AppServerDTO getAppKey(String serverName);

    AppServerDTO getServerName(String appKey);


    List<AppServerDTO> listAll();

    int listCount();

    AppServerDTO get(String id);

    int add(AppServerDTO appServerDTO);

    int upd(AppServerDTO appServerDTO);

    int del(String id);

}
