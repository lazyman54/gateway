package com.dafy.base.gateway.common.util;

import com.dafy.base.gateway.common.annotation.IgnoreFieldOnCopy;
import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/12
 */
public abstract class AbstractTransmitDtoTransferUtil<T> extends AbstractIgnoreTransferUtil<TransmitMapDTO, T> {

    protected AbstractTransmitDtoTransferUtil() {
        ignoreProperties = initIgnoreProperties();
        dubboInvocationFactory = initDubboInvocationFactory();
    }

    protected final String[] ignoreProperties;
    protected final InvocationFactory<T> dubboInvocationFactory;


    private final InvocationFactory<T> defaultEmptyFactory = new InvocationFactory<T>() {
        @Override
        public Invocation transferInvocation(T transmitModel) {
            return null;
        }

        @Override
        public void separateInvocation(Invocation invocation, T transmitModel) {

        }
    };


    private String[] initIgnoreProperties() {
        Class<T> cls = getTargetClass();
        Field[] fields = cls.getDeclaredFields();
        Set<String> ignoreFieldNames = new HashSet<>();
        for (Field field : fields) {
            IgnoreFieldOnCopy annotation = field.getAnnotation(IgnoreFieldOnCopy.class);
            if (annotation != null) {
                ignoreFieldNames.add(field.getName());
            }
        }
        return CollectionUtil.toArray(String.class, ignoreFieldNames);
    }

    protected abstract InvocationFactory<T> initDubboInvocationFactory();

    protected TransmitMapDTO transferToDto(T source) {
        TransmitMapDTO transmitMapDTO = new TransmitMapDTO();
        BeanUtils.copyProperties(source, transmitMapDTO, getIgnoreProperties());
        InvocationFactory<T> invocationFactory = getInvocationFactory(transmitMapDTO.getProtocol());

        transmitMapDTO.setInvocation(invocationFactory.transferInvocation(source));

        return transmitMapDTO;
    }

    protected T transferToT(Class<T> targetClass, TransmitMapDTO transmitMapDTO) throws IllegalAccessException, InstantiationException {
        T t = targetClass.newInstance();
        BeanUtils.copyProperties(transmitMapDTO, t, getIgnoreProperties());
        InvocationFactory<T> invocationFactory = getInvocationFactory(transmitMapDTO.getProtocol());

        invocationFactory.separateInvocation(transmitMapDTO.getInvocation(), t);

        return t;
    }


    protected interface InvocationFactory<T> {
        Invocation transferInvocation(T transmitModel);

        void separateInvocation(Invocation invocation, T transmitModel);
    }

    protected InvocationFactory<T> getInvocationFactory(TransmitProtocol protocol) {
        switch (protocol) {
            case HTTP:
                return getHttpInvocation();
            case DUBBO:
                return getDubboInvocation();
            case WEBSERVICE:
                return getWebServerInvocation();
            default:
                return getDefaultInvocation();
        }
    }

    protected abstract InvocationFactory<T> getDubboInvocation();

    protected InvocationFactory<T> getHttpInvocation() {
        return defaultEmptyFactory;
    }

    protected InvocationFactory<T> getWebServerInvocation() {
        return defaultEmptyFactory;
    }

    protected InvocationFactory<T> getDefaultInvocation() {
        return getDubboInvocation();
    }

    protected abstract Class<T> getTargetClass();

}
