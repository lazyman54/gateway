package com.dafy.base.gateway.manage.controller;

import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.common.domain.dto.AppServerDTO;
import com.dafy.base.gateway.manage.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/4
 */
@RestController
@RequestMapping("/base/app")
@Slf4j
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/list")
    public ResponseModel listAll() {

        log.info("list all app server");

        List<AppServerDTO> dtoList = appService.listAll();

        log.info("total app server count {}", dtoList.size());
        return ResponseModel.<List<AppServerDTO>>newBuilder().data(dtoList).code("0").build();
    }

    @GetMapping("/get/{id}")
    public ResponseModel get(@PathVariable(name = "id") String id) {

        log.info("get detail for app, app id: {}", id);

        AppServerDTO dto = appService.getDetail(id);

        log.info("get appServer {}", dto);

        return ResponseModel.newBuilder().data(dto).code("0").build();

    }

    @PostMapping("/add")
    public ResponseModel add(@RequestBody AppServerDTO appServerDTO) {
        log.info("add a new app, app: {}", appServerDTO);

        appService.add(appServerDTO);

        return ResponseModel.DEFAULT_RESPONSE;
    }

    @PostMapping("/upd")
    public ResponseModel upd(@RequestBody AppServerDTO appServerDTO) {
        log.info("upd a app, upd info: {}", appServerDTO);

        appService.upd(appServerDTO);

        return ResponseModel.DEFAULT_RESPONSE;
    }

    @PostMapping("/del/{id}")
    public ResponseModel del(@PathVariable(name = "id") String id) {
        log.info("del a app, app id: {} ", id);

        appService.del(id);

        return ResponseModel.DEFAULT_RESPONSE;
    }

}
