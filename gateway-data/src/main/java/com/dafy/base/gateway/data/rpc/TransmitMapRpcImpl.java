package com.dafy.base.gateway.data.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.dafy.base.gateway.common.domain.dto.TransmitMapDTO;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.common.util.AbstractIgnoreTransferUtil;
import com.dafy.base.gateway.common.util.CollectionUtil;
import com.dafy.base.gateway.data.api.ITransmitMapRpc;
import com.dafy.base.gateway.data.dao.mongodb.TransmitMapMapper;
import com.dafy.base.gateway.data.domain.TransmitMapMDO;
import com.dafy.base.gateway.data.util.TransmitDtoTransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/8
 */
@Service(version = "1.0.0")
@Component
public class TransmitMapRpcImpl implements ITransmitMapRpc {

    private static final Logger logger = LoggerFactory.getLogger(TransmitMapRpcImpl.class);

    private static final Pattern PATTERN = Pattern.compile("/(\\w+)$");

    private final TransmitMapMapper transmitMapMapper;

    private final MongoTemplate mongoTemplate;

    private final AbstractIgnoreTransferUtil<TransmitMapDTO, TransmitMapMDO> transferInstance;

    public TransmitMapRpcImpl(TransmitMapMapper transmitMapMapper, MongoTemplate mongoTemplate) {
        this.transmitMapMapper = transmitMapMapper;
        this.mongoTemplate = mongoTemplate;
        this.transferInstance = TransmitDtoTransferUtil.instance();
    }

    @Override
    public TransmitMapDTO getTransmitMap(String appKey, String httpUrl) {

        logger.info("get transmit map, appKey:[{}], httpUrl:[{}]", appKey, httpUrl);

        TransmitMapMDO transmitMapMDO = transmitMapMapper.findByAppKeyAndHttpUrl(appKey, httpUrl);

        if (transmitMapMDO == null) {

            Matcher matcher = PATTERN.matcher(httpUrl);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, "/{*}");
            }
            transmitMapMDO = transmitMapMapper.findByAppKeyAndHttpUrlAndUrlAdapter(appKey, sb.toString(), true);
        }
        if (transmitMapMDO == null) {
            logger.warn("找不到对应的转发映射，请核实，appKey:[{}],httpUrl", appKey, httpUrl);
            return null;
        }

        return transferInstance.transferToDto(transmitMapMDO, TransmitMapDTO.class);
    }

    @Override
    public int addTransmitMap(TransmitMapDTO transmitMapDTO) throws DafyBaseException {
        logger.info("add: {}", transmitMapDTO);

        TransmitMapMDO transmitMapMDO = transferInstance.transferFromDto(transmitMapDTO, TransmitMapMDO.class);
        transmitMapMDO.setOperator("lazyman");
        transmitMapMapper.save(transmitMapMDO);
        return 1;
    }

    @Override
    public List<TransmitMapDTO> listByAppKeyAndLevel(Collection<String> appKeys, Collection<String> level) {
        logger.info("list all transmit by appKey, and level, appKey:{}, level:[{}]", appKeys, level);

        Query query = new Query();
        if (CollectionUtil.isNotEmpty(appKeys)) {
            query.addCriteria(new Criteria("appKey").in(appKeys));
        }
        if (CollectionUtil.isNotEmpty(level)) {
            query.addCriteria(new Criteria("level").in(level));
        }
        return transferInstance.transferToDtoList(mongoTemplate.find(query, TransmitMapMDO.class), TransmitMapDTO.class);
    }

    @Override
    public List<TransmitMapDTO> listAll(String appKey, int pos, int count) {
        logger.info("list all transmit by appKey, appKey:{}, pos:[{}], count:[{}]", appKey, pos, count);
        Query query = Query.query(new Criteria("appKey").is(appKey));
        query.skip(pos).limit(count).with(new Sort(new Sort.Order(Sort.Direction.DESC, "id")));

        return transferInstance.transferToDtoList(mongoTemplate.find(query, TransmitMapMDO.class), TransmitMapDTO.class);
    }

    @Override
    public int count(String appKey) {
        logger.info("count transmit by appKey, appKey:{}", appKey);
        return transmitMapMapper.countByAppKey(appKey);
    }

    @Override
    public int add(TransmitMapDTO transmitMapDTO) throws DafyBaseException {
        logger.info("add transmit info, {}", transmitMapDTO);

        TransmitMapMDO transmitMapMDO = transferInstance.transferFromDto(transmitMapDTO, TransmitMapMDO.class);
        transmitMapMDO.setOperator("lazyman");
        transmitMapMapper.save(transmitMapMDO);
        return 1;
    }

    @Override
    public int delete(String appId) {
        logger.info("delete transmit by appId, appId:{}", appId);
        transmitMapMapper.delete(appId);
        return 1;
    }

    @Override
    public TransmitMapDTO getById(String appId) {
        logger.info("get transmit by appId, appId:{}", appId);
        return transferInstance.transferToDto(transmitMapMapper.findOne(appId), TransmitMapDTO.class);
    }
}
