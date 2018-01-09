package com.dafy.base.gateway.common.domain.dto;

import com.dafy.base.gateway.common.domain.enums.AppLevelEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
@Setter
@Getter
@ToString(callSuper = true)
public class AppServerDTO implements Serializable {

    private static final long serialVersionUID = 894052804899950968L;

    private String id;

    private String appKey;

    private String serverName;

    private AppLevelEnum level;

    private String secretKey;

}
