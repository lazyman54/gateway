package com.dafy.base.gateway.manage.util;

import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.ParamDefinition;
import com.dafy.base.gateway.common.domain.RpcInvocation;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.domain.enums.ReturnTypeEnum;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.common.util.AbstractTransmitDtoTransferUtil;
import com.dafy.base.gateway.manage.domain.ParamDefinitionVo;
import com.dafy.base.gateway.manage.domain.TransmitMapVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/10
 */
@Slf4j
public final class TransmitDtoTransferUtil extends AbstractTransmitDtoTransferUtil<TransmitMapVO> {

    private TransmitDtoTransferUtil() {
        super();
    }

    private static final TransmitDtoTransferUtil TRANSMIT_MODEL_TRANSFER_UTIL = new TransmitDtoTransferUtil();

    public static AbstractIgnoreTransferUtil<TransmitMapDTO, TransmitMapVO> instance() {
        return TRANSMIT_MODEL_TRANSFER_UTIL;
    }


    @Override
    public TransmitMapDTO transferToDto(TransmitMapVO t, Class<TransmitMapDTO> classK) {
        TransmitMapDTO dto = super.transferToDto(t, classK);

        dto.setSync(Boolean.valueOf(t.getSync()));
        dto.setUrlAdapter(Boolean.valueOf(t.getUrlAdapter()));

        InvocationFactory<TransmitMapVO> invocationFactory = getInvocationFactory(t.getProtocol());

        dto.setInvocation(invocationFactory.transferInvocation(t));

        return dto;

    }

    @Override
    public TransmitMapVO transferFromDto(TransmitMapDTO k, Class<TransmitMapVO> classT) {
        TransmitMapVO mvo = super.transferFromDto(k, classT);

        /**
         * 前端小姑凉要求这两个bool字段要给他字符串，也上报字符串，所以这里强行满足一波。毕竟人家是菇凉
         */
        mvo.setSync(String.valueOf(k.getSync()));
        mvo.setUrlAdapter(String.valueOf(k.getUrlAdapter()));

        InvocationFactory<TransmitMapVO> invocationFactory = getInvocationFactory(k.getProtocol());

        invocationFactory.separateInvocation(k.getInvocation(), mvo);
        return mvo;

    }

    @Override
    protected InvocationFactory<TransmitMapVO> initDubboInvocationFactory() {
        return new TransmitInvocationFactory();
    }


    @Override
    protected Class<TransmitMapVO> getTargetClass() {
        return TransmitMapVO.class;
    }

    @Override
    protected InvocationFactory<TransmitMapVO> getDubboInvocation() {
        return dubboInvocationFactory;
    }

    @Override
    protected String[] getIgnoreProperties() {
        return join(this.ignoreProperties, "sync", "urlAdapter");
    }

    private class TransmitInvocationFactory implements InvocationFactory<TransmitMapVO> {
        @Override
        public Invocation transferInvocation(TransmitMapVO transmitModel) {

            RpcInvocation rpcInvocation = new RpcInvocation();

            rpcInvocation.setInvokeClass(transmitModel.getInvokeClassName());
            rpcInvocation.setVersion(transmitModel.getVersion());
            rpcInvocation.setMethodName(transmitModel.getMethodName());
            rpcInvocation.setReturnTypeEnum(ReturnTypeEnum.OBJECT);
            rpcInvocation.setParamDefinitions(transferFromVoList(transmitModel.getParamDefinitions()));
            rpcInvocation.setParameterCount(transmitModel.getParamDefinitions().size());
            rpcInvocation.setRegistryCenter(transmitModel.getRegistryCenter());
            rpcInvocation.setTimeout(transmitModel.getTimeout());
            rpcInvocation.setTryTime(transmitModel.getTryTime());
            return rpcInvocation;
        }

        @Override
        public void separateInvocation(Invocation invocation, TransmitMapVO transmitModel) {
            transmitModel.setInvokeClassName(invocation.getInvokeClass());
            transmitModel.setVersion(invocation.getVersion());
            transmitModel.setMethodName(invocation.getMethodName());
            transmitModel.setParamDefinitions(transferToVoList(invocation.getParameterDefinition()));
            transmitModel.setRegistryCenter(invocation.getRegistryCenter());
            transmitModel.setTimeout(invocation.getTimeout());
            transmitModel.setTryTime(invocation.getTryTime());
        }

        private List<ParamDefinition> transferFromVoList(List<ParamDefinitionVo> paramDefinitionVos) {
            List<ParamDefinition> paramDefinitions = new ArrayList<>(paramDefinitionVos.size());

            for (ParamDefinitionVo paramDefinitionVo : paramDefinitionVos) {
                paramDefinitions.add(transferFromVo(paramDefinitionVo));
            }
            return paramDefinitions;
        }

        private List<ParamDefinitionVo> transferToVoList(List<ParamDefinition> paramDefinitions) {
            List<ParamDefinitionVo> paramDefinitionVos = new ArrayList<>(paramDefinitions.size());

            for (ParamDefinition paramDefinition : paramDefinitions) {
                paramDefinitionVos.add(transferToVo(paramDefinition));
            }
            return paramDefinitionVos;
        }


        private ParamDefinition transferFromVo(ParamDefinitionVo paramDefinitionVo) {
            ParamDefinition paramDefinition = new ParamDefinition();
            paramDefinition.setParamType(paramDefinitionVo.getParamType());
            paramDefinition.setParamName(paramDefinitionVo.getParamName());
            paramDefinition.setBaseType(Boolean.valueOf(paramDefinitionVo.getBaseType()));
            paramDefinition.setArgs(Boolean.valueOf(paramDefinitionVo.getArgs()));
            paramDefinition.setNecessary(Boolean.valueOf(paramDefinitionVo.getNecessary()));
            paramDefinition.setFromUrl(Boolean.valueOf(paramDefinitionVo.getFromUrl()));
            return paramDefinition;
        }

        private ParamDefinitionVo transferToVo(ParamDefinition paramDefinition) {
            ParamDefinitionVo paramDefinitionVo = new ParamDefinitionVo();

            paramDefinitionVo.setParamType(paramDefinition.getParamType());
            paramDefinitionVo.setParamName(paramDefinition.getParamName());
            paramDefinitionVo.setBaseType(String.valueOf(paramDefinition.getBaseType()));
            paramDefinitionVo.setArgs(String.valueOf(paramDefinition.getArgs()));
            paramDefinitionVo.setNecessary(String.valueOf(paramDefinition.getNecessary()));
            paramDefinitionVo.setFromUrl(String.valueOf(paramDefinition.getFromUrl()));
            return paramDefinitionVo;
        }


    }


}
