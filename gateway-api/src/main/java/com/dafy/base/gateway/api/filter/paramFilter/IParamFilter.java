package com.dafy.base.gateway.api.filter.paramFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/14
 */
public interface IParamFilter {

    String getAppKey(HttpServletRequest request) throws IOException;

    String getUrl(HttpServletRequest request) throws IOException;

    String getByParamKey(String paramKey, HttpServletRequest request) throws IOException;

}
