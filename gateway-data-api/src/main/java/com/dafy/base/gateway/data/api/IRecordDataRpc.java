package com.dafy.base.gateway.data.api;

import com.dafy.base.gateway.common.domain.dto.InvokeRecordDto;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/30
 */
public interface IRecordDataRpc {

    /**
     * 持久化数据
     *
     * @param invokeRecordDto 被持久化的数据
     * @param withIdBack      是否返回持久化id
     * @return 如果withIdBack=true 则返回数据id，如果插入失败，返回null，否则返回持久化成功的条数，1表示插入一条，0表示插入0条
     */
    String add(InvokeRecordDto invokeRecordDto, boolean withIdBack);

}
