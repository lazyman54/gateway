package com.dafy.base.gateway.service.core;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.dafy.base.gateway.common.domain.Invocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
@Component
public class DubboService {
    @Resource
    private ApplicationConfig applicationConfig;

    @Resource
    private RegistryConfig registryConfig;


    public GenericService get(Invocation invocation) {
        RegistryConfig registryConfig = getRegistryConfigOrDefault(invocation);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setGeneric(true);
        reference.setInterface(invocation.getInvokeClass());
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);

        setVersion(reference, invocation.getVersion());
        setTimeout(reference, invocation.getTimeout());
        setRetryTime(reference, invocation.getTryTime());

        return get(reference);
    }


    /**
     * get dubbo service with interface name and interface version.
     * if there is no interface service is found, it will return null.
     *
     * @param reference reference
     * @return dubbo generic service if service exist, otherwise null.
     */
    private GenericService get(ReferenceConfig<GenericService> reference) {


        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        // the ReferenceConfigCache will cache null, if the generic service is not exits.
        // If do not destroy generic service, cache will return null every time.
        if (null == genericService) {
            cache.destroy(reference);
        }
        return genericService;
    }

    private RegistryConfig getRegistryConfigOrDefault(Invocation invocation) {

        return StringUtils.isBlank(invocation.getRegistryCenter()) ? registryConfig : new RegistryConfig(invocation.getRegistryCenter());

    }

    private void setVersion(ReferenceConfig<GenericService> reference, String version) {
        if (StringUtils.isNotBlank(version)) {
            reference.setVersion(version);
        }
    }

    private void setTimeout(ReferenceConfig<GenericService> reference, Integer timeout) {
        if (timeout != null && timeout > 0) {
            reference.setTimeout(timeout);
        }
    }

    private void setRetryTime(ReferenceConfig<GenericService> reference, Integer retryTime) {
        if (retryTime != null) {
            reference.setRetries(retryTime);
        }
    }


}
