package com.dafy.base.gateway.manage.controller;

import com.dafy.base.gateway.common.domain.ResponseModel;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import com.dafy.base.gateway.manage.domain.TableVo;
import com.dafy.base.gateway.manage.domain.TransmitMapVO;
import com.dafy.base.gateway.manage.service.TransmitMapService;
import com.dafy.base.gateway.manage.util.JavaSourceCodeParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.net.URLDecoder.decode;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/10
 */
@RestController
@RequestMapping("/base/transmit")
@Slf4j
public class TransmitController {

    private final TransmitMapService transmitMapService;

    @Autowired
    public TransmitController(TransmitMapService transmitMapService) {
        this.transmitMapService = transmitMapService;
    }

    @GetMapping("/list/{appKey}")
    public ResponseModel listAll(@PathVariable("appKey") String appKey, Integer page, Integer count) throws DafyBaseException {
        log.info("list all transmit by appKey:{}, page:[{}],count:[{}]", appKey, page, count);
        if (page == null) {
            page = 1;
        }
        if (count == null) {
            count = 10;
        }

        TableVo.TableVoBuilder<TransmitMapVO> builder = TableVo.builder();
        builder.totalCount(transmitMapService.count(appKey)).currentPage(page).dataList(transmitMapService.listAll(appKey, page, count));

        return ResponseModel.newBuilder().data(builder.build()).code("0").build();
    }

    @GetMapping("/count/{appKey}")
    public ResponseModel listCount(@PathVariable("appKey") String appKey) {
        log.info("count transmit by appKey:{}", appKey);
        return ResponseModel.newBuilder().data(transmitMapService.count(appKey)).code("0").build();
    }

    @PostMapping("/add")
    public ResponseModel add(@RequestBody TransmitMapVO transmitMapVO, HttpServletRequest request) throws DafyBaseException {
        log.info("add: {}, param:[{}]", transmitMapVO, request.getParameter("paramDefinitions"));

        return ResponseModel.newBuilder().data(transmitMapService.upd(transmitMapVO)).code("0").build();
    }

    @PostMapping("/upd")
    public ResponseModel upd(@RequestBody TransmitMapVO transmitMapVO) throws DafyBaseException {
        log.info("upd transmit map, new TransmitMap obj:{}", transmitMapVO);
        return ResponseModel.newBuilder().data(transmitMapService.upd(transmitMapVO)).code("0").build();
    }

    @PostMapping("/del/{id}")
    public ResponseModel del(@PathVariable("id") String id) {
        log.info("del one transmit map, id:{}", id);
        transmitMapService.delete(id);
        return ResponseModel.newBuilder().data("ok").code("0").build();
    }

    @GetMapping("/get/{id}")
    public ResponseModel get(@PathVariable("id") String id) throws DafyBaseException {
        log.info("get one transmit map, id:{}", id);
        return ResponseModel.newBuilder().data(transmitMapService.get(id)).code("0").build();
    }

    @PostMapping(value = "/parse")
    public ResponseModel parseJavaSource(String javaSource) throws UnsupportedEncodingException {
        String decode = decode(javaSource, "UTF-8");

        JavaSourceCodeParser javaSourceCodeParser = new JavaSourceCodeParser(decode);

        return ResponseModel.<List<TransmitMapVO>>newBuilder().data(javaSourceCodeParser.parseJavaCode()).code("0").build();
    }

    @GetMapping("/search")
    public ResponseModel search(String searchStr) {
        TableVo.TableVoBuilder<TransmitMapVO> builder = TableVo.builder();
        builder.totalCount(0).currentPage(1).dataList(new ArrayList<>(0));

        return ResponseModel.newBuilder().data(builder.build()).code("0").build();
    }
}
