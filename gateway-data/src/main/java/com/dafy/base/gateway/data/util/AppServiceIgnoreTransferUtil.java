package com.dafy.base.gateway.data.util;

import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.data.domain.AppServerMDO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
public final class AppServiceIgnoreTransferUtil extends AbstractIgnoreTransferUtil<AppServerDTO, AppServerMDO> {

    private AppServiceIgnoreTransferUtil() {
    }

    private static final AppServiceIgnoreTransferUtil APP_SERVICE_TRANSFER_UTIL = new AppServiceIgnoreTransferUtil();

    public static AbstractIgnoreTransferUtil<AppServerDTO, AppServerMDO> instance() {
        return APP_SERVICE_TRANSFER_UTIL;
    }


    public List<AppServerDTO> getDtoListFromMdoList(List<AppServerMDO> appServerMDOS) {
        List<AppServerDTO> appServerDTOS = new ArrayList<>(appServerMDOS.size());

        for (AppServerMDO serverMDO : appServerMDOS) {
            appServerDTOS.add(transferToDto(serverMDO, AppServerDTO.class));
        }
        return appServerDTOS;
    }

    @Override
    public AppServerMDO transferFromDto(AppServerDTO k, Class<AppServerMDO> classT) {
        AppServerMDO mdo = super.transferFromDto(k, classT);
        Date curDate = new Date();

        mdo.setCreateTime(curDate);
        mdo.setUpdTime(curDate);
        mdo.setOperator("");

        return mdo;
    }
}
