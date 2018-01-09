package com.dafy.base.gateway.data.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.dafy.base.gateway.common.domain.dto.InvokeRecordDto;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.data.api.IRecordDataRpc;
import com.dafy.base.gateway.data.dao.mongodb.RecordDataMongoMapper;
import com.dafy.base.gateway.data.domain.InvokerRecordMdo;
import com.dafy.base.gateway.data.util.InvokeRecordDataTransferUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/30
 */
@Slf4j
@Service
@Component
public class RecordDataRpcImpl implements IRecordDataRpc {

    private final RecordDataMongoMapper recordDataMongoMapper;

    private final AbstractIgnoreTransferUtil<InvokeRecordDto, InvokerRecordMdo> transferUtil;


    public RecordDataRpcImpl(RecordDataMongoMapper recordDataMongoMapper) {
        this.recordDataMongoMapper = recordDataMongoMapper;
        this.transferUtil = InvokeRecordDataTransferUtil.instance();
    }

    @Override
    public String add(InvokeRecordDto invokeRecordDto, boolean withIdBack) {
        log.info("try to save data:[{}] into mongodb", invokeRecordDto);

        InvokerRecordMdo recordMdo = transferUtil.transferFromDto(invokeRecordDto, InvokerRecordMdo.class);

        recordMdo = recordDataMongoMapper.save(recordMdo);

        return recordMdo.getId();
    }
}
