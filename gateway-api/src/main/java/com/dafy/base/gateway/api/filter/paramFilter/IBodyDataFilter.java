package com.dafy.base.gateway.api.filter.paramFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/14
 */
public interface IBodyDataFilter extends IParamFilter {

    Map<String, Object> getParamMap(HttpServletRequest request) throws IOException;

    String getBodyOriginData(HttpServletRequest request) throws IOException;
}
