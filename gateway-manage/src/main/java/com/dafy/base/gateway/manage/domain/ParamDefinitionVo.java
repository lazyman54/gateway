package com.dafy.base.gateway.manage.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 顺应小姑凉的要求，把bool型全部改成字符串类型
 * {@link com.dafy.base.gateway.common.domain.ParamDefinition}
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/12/22
 */
@Getter
@Setter
@ToString
public class ParamDefinitionVo {
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
    private String necessary;

    /**
     * 参数是否是基础类型
     */
    private String baseType;

    /**
     * 参数是否是数组，如String... args
     */
    private String args;

    /**
     * 参数是否从url获取
     */
    private String fromUrl;
}
