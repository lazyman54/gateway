package com.dafy.base.gateway.record.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dafy.base.gateway.common.domain.dto.InvokeRecordDto;
import com.dafy.base.gateway.record.domin.Constants;
import com.dafy.base.gateway.record.service.InvokeRecordDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/11/28
 */
@Component
@Slf4j
public class RecordDataConsumer {


    private final InvokeRecordDataService invokeRecordDataService;

    @Autowired
    public RecordDataConsumer(InvokeRecordDataService invokeRecordDataService) {
        this.invokeRecordDataService = invokeRecordDataService;
    }


    @KafkaListener(topics = Constants.GATEWAY_INVOKER_TOPIC)
    public void consumer(String msg) {
        log.info("consume message: {}", msg);

        InvokeRecordDto invokerRecordMdo = makeRecordMdo(msg);

        invokeRecordDataService.add(invokerRecordMdo);


        //invokerRecordMdo = recordDataMongoMapper.save(invokerRecordMdo);
    }

    /**
     * 讲json格式的字符串转换为实际对象，字符串格式
     * <p>{"event":"gateway_invoke","params":{"duration":206,"exception":null,"stateEnum":{"name":"SUCCESS"},"appKey":"data-platform-ob","startTime":1511936314189,"endTime":1511936314395,"url":"/observer/event/commit"},"timestamp":1511936314395,"bizApp":"gateway","id":"D6C3_1711291103501200384","handlerState":1}</p>
     *
     * @param jsonStr json字符串
     * @return 返回mongodb存储对象
     */
    private InvokeRecordDto makeRecordMdo(String jsonStr) {
        JSONObject jsonObj;
        JSONObject paramDataJson;
        try {
            jsonObj = JSON.parseObject(jsonStr);
            paramDataJson = jsonObj.getJSONObject("params");
        } catch (Exception e) {
            log.error("json字符串转换失败，原json字符串：{}, 失败信息：{}", jsonStr, e);
            return null;
        }
        InvokeRecordDto invokerRecordDto = new InvokeRecordDto();
        invokerRecordDto.setAppKey(paramDataJson.getString("appKey"));
        invokerRecordDto.setBeginTime(paramDataJson.getLongValue("startTime"));
        invokerRecordDto.setEndTime(paramDataJson.getLongValue("endTime"));
        invokerRecordDto.setDuration(paramDataJson.getIntValue("duration"));
        invokerRecordDto.setHttpUrl(paramDataJson.getString("url"));
        invokerRecordDto.setInvokeStatus(paramDataJson.getJSONObject("stateEnum").getString("name"));
        invokerRecordDto.setException(paramDataJson.getString("exception"));
        invokerRecordDto.setTid(jsonObj.getString("id"));
        invokerRecordDto.setCreateTime(new Date());
        invokerRecordDto.setOperatorIp(paramDataJson.getString("ip"));

        invokerRecordDto.setEvent(jsonObj.getString("event"));
        invokerRecordDto.setBizApp(jsonObj.getString("bizApp"));
        invokerRecordDto.setHandlerState(jsonObj.getInteger("handlerState"));

        return invokerRecordDto;
    }

}
