package com.dafy.base.gateway.data.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.dafy.base.gateway.data.api.IMyTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/28
 */

@Service
@Component
@Slf4j
public class MyTest implements IMyTest {
    @Override
    public String testListStr(List<String> myList) {
        log.info("myList: {}", myList);
        return "finish";
    }
}
