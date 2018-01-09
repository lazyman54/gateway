package com.dafy.base.gateway.data.dao.mongodb;

import com.dafy.base.gateway.data.domain.AppServerMDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
public interface AppServiceMapper extends MongoRepository<AppServerMDO, String> {

    AppServerMDO findByServerName(String serverName);

    AppServerMDO findByAppKey(String appKey);

}
