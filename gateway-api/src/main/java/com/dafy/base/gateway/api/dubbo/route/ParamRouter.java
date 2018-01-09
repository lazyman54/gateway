package com.dafy.base.gateway.api.dubbo.route;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.dafy.base.gateway.common.domain.enums.AppLevelEnum;
import com.dafy.base.gateway.common.util.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/25
 */
public class ParamRouter implements Router {

    private static final Logger logger = LoggerFactory.getLogger(ParamRouter.class);

    private URL url;

    private final int priority;

    private final Map<AppLevelEnum, List<String>> providerMap;

    public ParamRouter(URL url) {
        this.url = url;
        this.priority = 5;
        providerMap = initProviderMap(url.getParameter("rule"));
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        String routeParam = RpcContext.getContext().getAttachment("route");
        String appLevel = RpcContext.getContext().getAttachment("level");
        logger.info("路由参数：[{}], app级别：[{}]", routeParam, appLevel);

        List<String> list = providerMap.getOrDefault(AppLevelEnum.valueOf(appLevel), new ArrayList<>());

        List<Invoker<T>> routeInvokers = new ArrayList<>();

        if (invokers.size() == 1) {
            return invokers;
        } else if (invokers.size() >= 2) {
            for (Invoker<T> invoker : invokers) {
                String host = invoker.getUrl().getHost();
                if (list.contains(host)) {
                    routeInvokers.add(invoker);
                }
            }
        }

        return routeInvokers;
    }

    @Override
    public int compareTo(Router o) {

        if (o == null || o.getClass() != ParamRouter.class) {
            return 1;
        }
        ParamRouter po = (ParamRouter) o;
        return Integer.compare(this.priority, po.priority);
    }

    private Map<AppLevelEnum, List<String>> initProviderMap(String rule) {
        //high_10.8.15.43;middle_10.8.15.43+10.8.15.42;low_10.8.15.42

        Map<AppLevelEnum, List<String>> appLevelEnumListMap = MapUtils.newHashMap();

        String[] ruleStrArr = StringUtils.split(rule, ";");
        for (String ruleStr : ruleStrArr) {
            String[] levelStrArr = StringUtils.split(ruleStr, "_");
            String[] ipStrArr = StringUtils.split(levelStrArr[1], "+");
            AppLevelEnum appLevelEnum = AppLevelEnum.valueOf(levelStrArr[0].toUpperCase());
            appLevelEnumListMap.put(appLevelEnum, Arrays.asList(ipStrArr));
        }
        return appLevelEnumListMap;
    }

}
