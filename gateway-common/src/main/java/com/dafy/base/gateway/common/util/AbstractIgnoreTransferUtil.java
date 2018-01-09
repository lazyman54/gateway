package com.dafy.base.gateway.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
public abstract class AbstractIgnoreTransferUtil<DTO, MDO> {

    protected String[] ignoreProperties = new String[]{"createTime", "updTime", "operator"};

    protected String[] getIgnoreProperties() {
        return ignoreProperties;
    }


    public List<DTO> transferToDtoList(List<MDO> mdoList, Class<DTO> dtoClass) {

        if (CollectionUtil.isEmpty(mdoList)) {
            return new ArrayList<>(0);
        }

        List<DTO> dtoList = new ArrayList<>(mdoList.size());
        mdoList.forEach(mdo -> dtoList.add(transferToDto(mdo, dtoClass)));
        return dtoList;
    }


    public List<MDO> transferFromDtoList(List<DTO> dtoList, Class<MDO> mdoClass) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return new ArrayList<>(0);
        }
        List<MDO> mdoList = new ArrayList<>(dtoList.size());
        dtoList.forEach(dto -> mdoList.add(transferFromDto(dto, mdoClass)));
        return mdoList;
    }

    public DTO transferToDto(MDO t, Class<DTO> classK) {
        DTO k;
        try {
            k = classK.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("目标对象instance失败");
        }

        BeanUtils.copyProperties(t, k, getIgnoreProperties());

        return k;
    }

    public MDO transferFromDto(DTO k, Class<MDO> classT) {
        MDO t;
        try {
            t = classT.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("目标对象instance失败");
        }
        BeanUtils.copyProperties(k, t, getIgnoreProperties());
        return t;
    }

    protected String[] join(String[] originStrArray, String... joinStr) {

        if (joinStr == null || joinStr.length == 0) {
            return originStrArray;
        }

        String[] newStrArray = new String[originStrArray.length + joinStr.length];

        for (int i = 0; i < newStrArray.length; i++) {

            if (i < originStrArray.length) {
                newStrArray[i] = originStrArray[i];
            } else {
                int j = i % originStrArray.length;
                newStrArray[i] = joinStr[j];
            }
        }
        return newStrArray;

    }

}
