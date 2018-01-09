package com.dafy.base.gateway.api.dubbo.route;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;
import com.dafy.base.gateway.api.holder.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/25
 */
public class ParamRouteFactory implements RouterFactory {
    private static final Logger logger = LoggerFactory.getLogger(ParamRouteFactory.class);

    @Override
    public Router getRouter(URL url) {
        logger.info("url: [{}]", url);

        logger.info("....{}", ApplicationContextHolder.getContext());

        return new ParamRouter(url);
    }
}
