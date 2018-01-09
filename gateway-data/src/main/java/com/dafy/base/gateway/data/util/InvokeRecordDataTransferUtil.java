package com.dafy.base.gateway.data.util;

import com.dafy.base.gateway.common.domain.dto.InvokeRecordDto;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.common.util.CollectionUtil;
import com.dafy.base.gateway.data.domain.InvokerRecordMdo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/30
 */
public class InvokeRecordDataTransferUtil extends AbstractIgnoreTransferUtil<InvokeRecordDto, InvokerRecordMdo> {

    private static final String[] IGNORE_PROPERTIES = {"id", "event", "bizApp", "handlerState"};


    private InvokeRecordDataTransferUtil() {
    }

    private static final AbstractIgnoreTransferUtil<InvokeRecordDto, InvokerRecordMdo> APP_SERVICE_TRANSFER_UTIL = new InvokeRecordDataTransferUtil();

    public static AbstractIgnoreTransferUtil<InvokeRecordDto, InvokerRecordMdo> instance() {
        return APP_SERVICE_TRANSFER_UTIL;
    }


    public List<InvokerRecordMdo> transferFromDto(List<InvokeRecordDto> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return new ArrayList<>(0);
        }

        List<InvokerRecordMdo> mdoList = new ArrayList<>(dtoList.size());

        for (InvokeRecordDto invokeRecordDto : dtoList) {
            mdoList.add(transferFromDto(invokeRecordDto, InvokerRecordMdo.class));
        }

        return mdoList;
    }


    @Override
    public InvokerRecordMdo transferFromDto(InvokeRecordDto k, Class<InvokerRecordMdo> classT) {
        InvokerRecordMdo mdo = super.transferFromDto(k, classT);
        mdo.setCreateTime(new Date());
        return mdo;

    }


    @Override
    protected String[] getIgnoreProperties() {
        return join(super.getIgnoreProperties(), IGNORE_PROPERTIES);
    }

    public InvokerRecordMdo transferFromDto(InvokeRecordDto dto) {

        InvokerRecordMdo invokerRecordMdo = new InvokerRecordMdo();

        BeanUtils.copyProperties(dto, invokerRecordMdo, IGNORE_PROPERTIES);

        return invokerRecordMdo;
    }


}
