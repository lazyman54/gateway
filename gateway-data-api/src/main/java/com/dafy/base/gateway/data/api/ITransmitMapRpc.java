package com.dafy.base.gateway.data.api;

import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.exception.DafyBaseException;

import java.util.Collection;
import java.util.List;

/**
 * 服务转发映射数据操作接口
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/8/7
 */
public interface ITransmitMapRpc {

    TransmitMapDTO getTransmitMap(String appKey, String httpUrl);

    int addTransmitMap(TransmitMapDTO transmitMapDTO) throws DafyBaseException;

    List<TransmitMapDTO> listByAppKeyAndLevel(Collection<String> appKey, Collection<String> level);

    List<TransmitMapDTO> listAll(String appKey, int page, int count);

    int count(String appKey);

    int add(TransmitMapDTO transmitMapDTO) throws DafyBaseException;

    int delete(String appId);

    TransmitMapDTO getById(String appId);

}
