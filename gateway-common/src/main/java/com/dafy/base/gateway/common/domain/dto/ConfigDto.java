package com.dafy.base.gateway.common.domain.dto;

import com.dafy.base.gateway.common.domain.enums.AppLevelEnum;
import com.dafy.base.gateway.common.domain.enums.SwitchEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 接口配置信息
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/12/5
 */
@Setter
@Getter
@ToString
public class ConfigDto implements Serializable {

    private static final long serialVersionUID = 228050995243758880L;
    private String appKey;

    private String httpUrl;

    private SwitchEnum enableSwitch;

    private int tps;

    private AppLevelEnum level;

}
