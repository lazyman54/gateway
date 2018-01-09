package com.dafy.base.gateway.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 参数定义类
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/18
 */
@Getter
@Setter
@ToString
public class ParamDefinition implements Serializable {

    private static final long serialVersionUID = -4457195886114452931L;

    /**
     * 参数的类型，如String.class.getName
     */
    private String paramType;
    /**
     * 参数的名字，如userId等
     */
    private String paramName;

    /**
     * 参数是否是必须的
     */
    private Boolean necessary = true;

    /**
     * 参数是否是基础类型
     */
    private Boolean baseType = false;

    /**
     * 参数是否是数组，如String... args
     */
    private Boolean args = false;

    /**
     * 参数是否从url获取
     */
    private Boolean fromUrl = false;
}
