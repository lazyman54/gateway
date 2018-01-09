package com.dafy.base.gateway.manage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/5
 */
@Getter
@Setter
@ToString
@Builder
public class TableVo<T> {

    private int totalCount;

    private int currentPage;

    private List<T> dataList;

}
