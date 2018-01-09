package com.dafy.base.gateway.data.api;

import com.dafy.base.gateway.common.domain.dto.ConfigDto;

import java.util.List;
import java.util.Map;

/**
 * <p>获取一些配置项值的接口</p>
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
public interface IConfigRpc {

    String getValue(String key);

    String testMyInterface(List<String> myList);

    Map<String, Object> getValues(String key, String... otherKeys);

    /**
     * 根据appKey和对应的url来获取对应的配置类
     *
     * @param appKey appKey
     * @param url    服务调用的url
     * @return 返回配置类
     */
    ConfigDto getConfig(String appKey, String url);


}
