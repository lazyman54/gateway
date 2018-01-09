package com.dafy.base.gateway.manage.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.data.api.ITransmitMapRpc;
import com.dafy.base.gateway.manage.domain.TransmitMapVO;
import com.dafy.base.gateway.manage.util.TransmitDtoTransferUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/10
 */
@Service
public class TransmitMapService {

    @Reference(version = "1.0.0")
    private ITransmitMapRpc transmitMapRpc;

    private final AbstractIgnoreTransferUtil<TransmitMapDTO, TransmitMapVO> transferInstance;

    public TransmitMapService() {
        this.transferInstance = TransmitDtoTransferUtil.instance();
    }

    /**
     * 新增方法 {@link TransmitMapService#upd(TransmitMapVO)}
     *
     * @param transmitMapVO 新增的对象
     * @throws DafyBaseException
     */
    @Deprecated
    public int add(TransmitMapVO transmitMapVO) throws DafyBaseException {

        TransmitMapDTO transmitMapDTO = transferInstance.transferToDto(transmitMapVO, TransmitMapDTO.class);

        return transmitMapRpc.addTransmitMap(transmitMapDTO);
    }

    public List<TransmitMapVO> listAll(String appKey, int page, int count) throws DafyBaseException {

        int pos = (page - 1) * count;

        return transferInstance.transferFromDtoList(transmitMapRpc.listAll(appKey, pos, count), TransmitMapVO.class);
    }

    public int count(String appKey) {
        return transmitMapRpc.count(appKey);
    }

    public String upd(TransmitMapVO transmitMapVO) throws DafyBaseException {
        TransmitMapDTO transmitMapDTO = transferInstance.transferToDto(transmitMapVO, TransmitMapDTO.class);
        return transmitMapRpc.add(transmitMapDTO) == 1 ? "操作成功" : "操作失败";
    }

    public int delete(String appId) {
        return transmitMapRpc.delete(appId);
    }

    public TransmitMapVO get(String appId) throws DafyBaseException {
        TransmitMapDTO dto = transmitMapRpc.getById(appId);
        return transferInstance.transferFromDto(dto, TransmitMapVO.class);
    }

}
