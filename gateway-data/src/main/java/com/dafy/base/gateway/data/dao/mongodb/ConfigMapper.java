package com.dafy.base.gateway.data.dao.mongodb;

import com.dafy.base.gateway.data.domain.ConfigMdo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
public interface ConfigMapper extends MongoRepository<ConfigMdo, String> {

   /* *//**
     *  aaa
     * @param key ad
     * @return
     *//*
    List<ConfigMdo> findAllByKeyIn(Collection<String> key);*/


    /**
     * 根据appKey和url唯一定位一个config配置文件
     *
     * @param appKey appKey
     * @param url    url
     * @return 返回定位到的配置文件
     */
    ConfigMdo findByAppKeyAndUrl(String appKey, String url);

}
