package com.dafy.base.gateway.api.filter;

import com.dafy.base.gateway.common.domain.constants.errorCode.ServerErrorCode;
import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.common.util.CollectionUtil;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Optional;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/11
 */
public class AppRecognizeMapHolder {

    private ImmutableMap<String, Optional<AppServerDTO>> serverMap;


    void init(List<AppServerDTO> mapList) {

        ImmutableMap.Builder<String, Optional<AppServerDTO>> builder = ImmutableMap.builder();

        if (CollectionUtil.isEmpty(mapList)) {
            serverMap = builder.build();
            return;
        }

        mapList.forEach((serverApp) -> builder.put(serverApp.getServerName(), Optional.of(serverApp)));

        serverMap = builder.build();
    }

    AppServerDTO get(String serverHost) throws Throwable {
        Optional<AppServerDTO> appMap = serverMap.get(serverHost);
        return appMap.orElseThrow(() -> new DafyBaseException(ServerErrorCode.SERVER_NOT_RECOGNIZE, "服务无法识别"));
    }

    String getAppKey(String serverHost) {
        Optional<AppServerDTO> appMap = serverMap.get(serverHost);
        if (appMap.isPresent()) {
            return appMap.get().getAppKey();
        }
        return null;
    }

    String getSecretKey(String serverHost) {
        Optional<AppServerDTO> appMap = serverMap.get(serverHost);

        if (appMap.isPresent()) {
            return appMap.get().getSecretKey();
        }
        return null;

    }

    /**
     * 根绝key获取值
     *
     * @param key key也就是server name
     * @return 返回对应的值，如没有映射则返回null
     */
    /*String getValue(String key) {
        return serverMap.get(key);
    }*/

}
