package com.dafy.base.gateway.data.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.data.api.IAppKeyRpc;
import com.dafy.base.gateway.data.dao.mongodb.AppServiceMapper;
import com.dafy.base.gateway.data.domain.AppServerMDO;
import com.dafy.base.gateway.data.util.AppServiceIgnoreTransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
@Service(version = "1.0.0")
@Component
public class AppKeyRpcImpl implements IAppKeyRpc {

    private static final Logger logger = LoggerFactory.getLogger(AppKeyRpcImpl.class);

    private final AppServiceMapper appServiceMapper;

    private AbstractIgnoreTransferUtil<AppServerDTO, AppServerMDO> instance;

    public AppKeyRpcImpl(AppServiceMapper appServiceMapper) {
        this.appServiceMapper = appServiceMapper;
        instance = AppServiceIgnoreTransferUtil.instance();
    }

    @Override
    public AppServerDTO getAppKey(String serverName) {
        logger.info("get app key by serverName:[{}]", serverName);

        AppServerMDO serverMDO = appServiceMapper.findByServerName(serverName);

        if (serverMDO == null) {
            logger.warn("没有serverName对应的appKey");
            return null;
        }

        return instance.transferToDto(serverMDO, AppServerDTO.class);

    }

    @Override
    public AppServerDTO getServerName(String appKey) {
        logger.info("get server name by app key:[{}]", appKey);
        AppServerMDO serverMDO = appServiceMapper.findByAppKey(appKey);
        if (serverMDO == null) {
            logger.warn("没有appKey对应的serverName");
            return null;
        }
        return instance.transferToDto(serverMDO, AppServerDTO.class);
    }

    @Override
    public List<AppServerDTO> listAll() {
        logger.info("list all app-server map");

        return instance.transferToDtoList(appServiceMapper.findAll(), AppServerDTO.class);
    }

    @Override
    public int listCount() {
        return (int) appServiceMapper.count();
    }

    @Override
    public AppServerDTO get(String id) {
        return instance.transferToDto(appServiceMapper.findOne(id), AppServerDTO.class);
    }

    @Override
    public int add(AppServerDTO appServerDTO) {

        appServiceMapper.save(instance.transferFromDto(appServerDTO, AppServerMDO.class));

        return 1;
    }

    @Override
    public int upd(AppServerDTO appServerDTO) {
        return 0;
    }

    @Override
    public int del(String id) {
        return 0;
    }
}
