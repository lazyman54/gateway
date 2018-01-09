package com.dafy.base.gateway.service.impl;

import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import com.dafy.base.gateway.service.InvokeService;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
@Component
public class InvokerServiceFactory {

    //private final List<InvokeService> invokeServices;

    private final ImmutableMap<TransmitProtocol, InvokeService> serviceMap;

    public InvokerServiceFactory(List<InvokeService> invokeServices) {
        //this.invokeServices = invokeServices;
        ImmutableMap.Builder<TransmitProtocol, InvokeService> builder = ImmutableMap.builder();

        for (InvokeService service : invokeServices) {
            builder.put(service.getProtocol(), service);
        }

        this.serviceMap = builder.build();
    }

    public InvokeService getInvokeService(TransmitProtocol transmitProtocol) {
        return serviceMap.get(transmitProtocol);
    }


}
