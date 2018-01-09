package com.dafy.base.gateway.data.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.dafy.base.gateway.common.domain.dto.ConfigDto;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.data.api.IConfigRpc;
import com.dafy.base.gateway.data.dao.mongodb.ConfigMapper;
import com.dafy.base.gateway.data.dao.mongodb.TransmitMapMapper;
import com.dafy.base.gateway.data.domain.TransmitMapMDO;
import com.dafy.base.gateway.data.util.ConfigTransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
@Service(version = "1.0.0")
@Component
public class ConfigRpcImpl implements IConfigRpc {

    private final static Logger logger = LoggerFactory.getLogger(ConfigRpcImpl.class);

    private final ConfigMapper configMapper;

    private final TransmitMapMapper transmitMapMapper;

    private AbstractIgnoreTransferUtil<ConfigDto, TransmitMapMDO> instance;

    public ConfigRpcImpl(ConfigMapper configMapper, TransmitMapMapper transmitMapMapper) {
        this.configMapper = configMapper;
        this.transmitMapMapper = transmitMapMapper;
        this.instance = ConfigTransferUtil.instance();
    }

    @Override
    public ConfigDto getConfig(String appKey, String url) {
        logger.info("get config by appKey:[{}] and url:[{}]", appKey, url);
        //ConfigMdo configMdo = configMapper.findByAppKeyAndUrl(appKey, url);

        TransmitMapMDO transmitMapMDO = transmitMapMapper.findByAppKeyAndHttpUrl(appKey, url);

        return instance.transferToDto(transmitMapMDO, ConfigDto.class);
    }

    @Override
    public String getValue(String key) {
        logger.info("get value. key:[{}]", key);

        return "";
    }

    @Override
    public String testMyInterface(List<String> myList) {
        logger.info("myList:{}", myList);
        return null;
    }

    @Override
    public Map<String, Object> getValues(String key, String... otherKeys) {
        logger.info("get values. key:[{}], keys:[{}]", key, otherKeys);

        /*if (otherKeys == null || otherKeys.length == 0) {
            return MapUtils.getHashMap(key, getValue(key));
        }

        List<ConfigMdo> mdoList = configMapper.findAllByKeyIn(Lists.asList(key, otherKeys));

        if (CollectionUtil.isEmpty(mdoList)) {
            return new HashMap<>(0);
        }

        Map<String, Object> resultMap = MapUtils.newHashMap(mdoList.size() * 2);
        *//*for (ConfigMdo mdo : mdoList) {
            resultMap.put(mdo.getKey(), mdo.getValue());
        }*/

        return null;
    }
}
