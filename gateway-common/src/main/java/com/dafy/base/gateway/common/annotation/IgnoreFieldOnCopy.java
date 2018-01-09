package com.dafy.base.gateway.common.annotation;

import java.lang.annotation.*;

/**
 * 加上此注解的属性在做对象转换时会被忽略
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/9/12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreFieldOnCopy {
}
