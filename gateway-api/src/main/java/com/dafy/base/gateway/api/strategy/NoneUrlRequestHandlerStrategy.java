package com.dafy.base.gateway.api.strategy;

import org.springframework.stereotype.Component;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/7
 */
@Component
public class NoneUrlRequestHandlerStrategy {


    public boolean isNoneUrlRequestAllow() {
        return false;
    }

}
