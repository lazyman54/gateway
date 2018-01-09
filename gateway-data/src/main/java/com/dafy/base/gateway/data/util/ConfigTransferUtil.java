package com.dafy.base.gateway.data.util;

import com.dafy.base.gateway.common.domain.dto.ConfigDto;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.data.domain.TransmitMapMDO;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/5
 */
public class ConfigTransferUtil extends AbstractIgnoreTransferUtil<ConfigDto, TransmitMapMDO> {

    private ConfigTransferUtil() {
    }

    private static final ConfigTransferUtil CONFIG_TRANSFER_UTIL = new ConfigTransferUtil();

    public static AbstractIgnoreTransferUtil<ConfigDto, TransmitMapMDO> instance() {
        return CONFIG_TRANSFER_UTIL;
    }

    /*@Override
    public ConfigDto transferToDto(TransmitMapMDO configMdo, Class<ConfigDto> classK) {
        ConfigDto configDto = super.transferToDto(configMdo, classK);
        //configDto.setEnableSwitch(SwitchEnum.fromKey(configMdo.getEnableSwitch()));
        return configDto;
    }

    @Override
    public TransmitMapMDO transferFromDto(ConfigDto configDto, Class<TransmitMapMDO> classT) {
        return super.transferFromDto(configDto, classT);
    }

    @Override
    protected String[] getIgnoreProperties() {
        String[] ignoreProperties = super.getIgnoreProperties();
        return join(ignoreProperties, "enableSwitch");
    }*/
}
