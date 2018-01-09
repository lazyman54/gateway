package com.dafy.base.gateway.manage.controller;

import com.dafy.base.gateway.common.domain.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/12/4
 */
@RestController
@RequestMapping("/base/user")
@Slf4j
public class LoginController {


    @PostMapping("/login")
    public ResponseModel login(String userName, String password) {
        log.info("用户登录命令, 用户名：[{}], 密码：[{}]", userName, password);

        if (Strings.isNullOrEmpty(userName)) {
            userName = "无名氏";
        }
        return ResponseModel.newBuilder().data(userName).build();

    }

}
