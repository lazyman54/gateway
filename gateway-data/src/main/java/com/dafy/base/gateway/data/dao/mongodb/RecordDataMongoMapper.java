package com.dafy.base.gateway.data.dao.mongodb;

import com.dafy.base.gateway.data.domain.InvokerRecordMdo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/30
 */
public interface RecordDataMongoMapper extends MongoRepository<InvokerRecordMdo, String> {
}
