package com.dafy.base.gateway.data.util;

import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.RpcInvocation;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.common.util.AbstractTransmitDtoTransferUtil;
import com.dafy.base.gateway.data.domain.TransmitMapMDO;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
@Slf4j
public final class TransmitDtoTransferUtil extends AbstractTransmitDtoTransferUtil<TransmitMapMDO> {

    private TransmitDtoTransferUtil() {
        super();
    }

    private static final TransmitDtoTransferUtil TRANSMIT_MODEL_TRANSFER_UTIL = new TransmitDtoTransferUtil();


    public static AbstractIgnoreTransferUtil<TransmitMapDTO, TransmitMapMDO> instance() {
        return TRANSMIT_MODEL_TRANSFER_UTIL;
    }

    @Override
    protected InvocationFactory<TransmitMapMDO> initDubboInvocationFactory() {
        return new InvocationFactory<TransmitMapMDO>() {
            @Override
            public Invocation transferInvocation(TransmitMapMDO transmitModel) {

                RpcInvocation rpcInvocation = new RpcInvocation();

                rpcInvocation.setInvokeClass(transmitModel.getInvokeClass());
                rpcInvocation.setVersion(transmitModel.getVersion());
                rpcInvocation.setMethodName(transmitModel.getMethodName());
                rpcInvocation.setReturnTypeEnum(transmitModel.getReturnTypeEnum());
                rpcInvocation.setParamDefinitions(transmitModel.getParamDefinitions());
                rpcInvocation.setParameterCount(transmitModel.getParameterCount());
                rpcInvocation.setRegistryCenter(transmitModel.getRegistryCenter());
                rpcInvocation.setTimeout(transmitModel.getTimeout());
                rpcInvocation.setTryTime(transmitModel.getTryTime());
                return rpcInvocation;
            }

            @Override
            public void separateInvocation(Invocation invocation, TransmitMapMDO transmitModel) {
                transmitModel.setInvokeClass(invocation.getInvokeClass());
                transmitModel.setVersion(invocation.getVersion());
                transmitModel.setMethodName(invocation.getMethodName());
                transmitModel.setReturnTypeEnum(invocation.getReturnType());
                transmitModel.setParamDefinitions(invocation.getParameterDefinition());
                transmitModel.setParameterCount(invocation.getParameterCount());
                transmitModel.setRegistryCenter(invocation.getRegistryCenter());
                transmitModel.setTimeout(invocation.getTimeout());
                transmitModel.setTryTime(invocation.getTryTime());
            }
        };
    }

    @Override
    public TransmitMapDTO transferToDto(TransmitMapMDO t, Class<TransmitMapDTO> classK) {
        TransmitMapDTO dto = super.transferToDto(t, classK);
        InvocationFactory<TransmitMapMDO> invocationFactory = getInvocationFactory(t.getProtocol());

        dto.setInvocation(invocationFactory.transferInvocation(t));
        return dto;
    }

    @Override
    public TransmitMapMDO transferFromDto(TransmitMapDTO k, Class<TransmitMapMDO> classT) {
        TransmitMapMDO mdo = super.transferFromDto(k, classT);
        Date date = new Date();
        mdo.setCreateTime(date);
        mdo.setUpdTime(date);
        InvocationFactory<TransmitMapMDO> invocationFactory = getInvocationFactory(k.getProtocol());
        invocationFactory.separateInvocation(k.getInvocation(), mdo);

        return mdo;
    }

    @Override
    protected InvocationFactory<TransmitMapMDO> getDubboInvocation() {
        return dubboInvocationFactory;
    }

    @Override
    protected String[] getIgnoreProperties() {
        return this.ignoreProperties;
    }

    @Override
    protected Class<TransmitMapMDO> getTargetClass() {
        return TransmitMapMDO.class;
    }
}
