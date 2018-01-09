package com.dafy.base.gateway.record.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dafy.base.gateway.common.domain.dto.InvokeRecordDto;
import com.dafy.base.gateway.data.api.IRecordDataRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/30
 */
@Service
@Slf4j
public class InvokeRecordDataService {

    @Reference(version = "1.0.0")
    private IRecordDataRpc recordDataRpc;

    public void add(InvokeRecordDto invokeRecordDto) {
        String id = recordDataRpc.add(invokeRecordDto, true);

        if (StringUtils.isBlank(id)) {
            log.warn("持久化数据失败，请稍后重试,数据:{}", invokeRecordDto);
            throw new RuntimeException("持久化数据失败，稍后重试");
        }

        if (log.isDebugEnabled()) {
            log.debug("save invoke record into db success, mongodb id:[{}], tid:[{}]", id, invokeRecordDto.getTid());
        }


    }
}