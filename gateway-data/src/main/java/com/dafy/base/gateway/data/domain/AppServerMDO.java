package com.dafy.base.gateway.data.domain;

import com.dafy.base.gateway.common.domain.enums.AppLevelEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * <p>appKey-serverName映射实体类</p>
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
@Document(collection = "AppServer")
@Getter
@Setter
@ToString
public class AppServerMDO {

    @Id
    private String id;

    private String appKey;

    @Indexed(unique = true, name = "UDX_SERVER")
    private String serverName;

    private AppLevelEnum level;

    private String secretKey;

    private Date createTime;

    private Date updTime;

    private String operator;

}
