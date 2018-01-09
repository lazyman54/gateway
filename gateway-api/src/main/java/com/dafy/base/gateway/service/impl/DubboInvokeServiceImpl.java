package com.dafy.base.gateway.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dafy.base.gateway.common.domain.Invocation;
import com.dafy.base.gateway.common.domain.ParamDefinition;
import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import com.dafy.base.gateway.service.core.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lazyman
 * @version v1.0
 */
@Service
public class DubboInvokeServiceImpl extends AbstractInvokeServiceImpl {

    @Resource
    private DubboService dubboService;

    private static final Logger log = LoggerFactory.getLogger(DubboInvokeServiceImpl.class);

    @Override
    public TransmitProtocol getProtocol() {
        return TransmitProtocol.DUBBO;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object syncInvoke(Invocation invocation, Map<String, Object> param) {

        GenericService genericService = dubboService.get(invocation);

        if (null == genericService) {
            log.warn("can not find dubbo service {}, version:{}", invocation.getInvokeClass(), invocation.getVersion());
            return JSON.toJSONString(SERVICE_NOT_EXIST_RETURN_RESULT);
        }

        List<ParamDefinition> paramTypeList = invocation.getParameterDefinition();

        return genericService.$invoke(invocation.getMethodName(),
                getMethodParamArray(paramTypeList), getParam(paramTypeList, param));
    }

    private String[] getMethodParamArray(List<ParamDefinition> paramTypeList) {

        if (CollectionUtils.isEmpty(paramTypeList)) {
            return new String[0];
        }

        String[] methodParamArray = new String[paramTypeList.size()];

        for (int i = 0; i < paramTypeList.size(); i++) {
            String paramType = paramTypeList.get(i).getParamType();
            paramType = paramType.replaceAll("<.*?>", "");
            methodParamArray[i] = paramType;
        }
        return methodParamArray;
    }

    private Object[] getParam(List<ParamDefinition> paramTypeList, Map<String, Object> param) {

        if (CollectionUtils.isEmpty(paramTypeList)) {
            return new Object[0];
        }
        Object[] objects = new Object[paramTypeList.size()];

        for (int i = 0; i < paramTypeList.size(); i++) {
            ParamDefinition definition = paramTypeList.get(i);

            if (definition.getBaseType()) {
                objects[i] = param.get(definition.getParamName());
            } else if (definition.getArgs()) {
                // TODO: 2017/12/28 maojinlin 这里要兼容客户端List<Object>的样式，目前因为需求紧急，暂时处理List<String>的样式，通过args字段标记

                JSONArray array = JSON.parseArray(param.get(definition.getParamName()).toString());
                objects[i] = array.toArray();
                //objects[i] = param.get(definition.getParamName());

            } else {
                if (param.get("originData") != null) {
                    objects[i] = JSON.parseObject(param.get("originData").toString());
                } else {
                    objects[i] = param;
                }
            }
        }

        return objects;
    }

    @Override
    public Object asyncInvoke(Invocation invocation, Map<String, Object> param) {
        return UNSUPPORTED_RETURN_RESULT;
    }
}
