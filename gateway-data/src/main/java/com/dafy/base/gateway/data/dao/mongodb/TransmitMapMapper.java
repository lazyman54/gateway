package com.dafy.base.gateway.data.dao.mongodb;

import com.dafy.base.gateway.data.domain.TransmitMapMDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
public interface TransmitMapMapper extends MongoRepository<TransmitMapMDO, String> {


    /**
     * 通过appKey和httpUrl精确匹配转化接口
     *
     * @param appKey  appKey
     * @param httpUrl httpUrl
     * @return 返回转化类
     */
    TransmitMapMDO findByAppKeyAndHttpUrl(String appKey, String httpUrl);

    /**
     * 通过appKey和httpUrl另外加urlAdapter来精确匹配转化接口
     *
     * @param appKey     appKey
     * @param httpUrl    httpUrl
     * @param urlAdapter 是否是url参数适配器类
     * @return 返回转化类
     */
    TransmitMapMDO findByAppKeyAndHttpUrlAndUrlAdapter(String appKey, String httpUrl, Boolean urlAdapter);

    List<TransmitMapMDO> findByAppKey(String appKey);

    /**
     * 查此appKey下有多少个暴露接口
     *
     * @param appKey
     * @return
     */
    int countByAppKey(String appKey);
}
