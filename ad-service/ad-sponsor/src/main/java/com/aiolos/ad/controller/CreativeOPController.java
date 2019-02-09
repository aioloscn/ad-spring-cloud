package com.aiolos.ad.controller;

import com.aiolos.ad.exception.AdException;
import com.aiolos.ad.service.ICreativeService;
import com.aiolos.ad.vo.CreativeRequest;
import com.aiolos.ad.vo.CreativeResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aiolos
 * @date 2019-02-01 17:08
 */
@Slf4j
@RestController
public class CreativeOPController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/cretive")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException {

        log.info("ad-sponsor: createCreative -> {}", JSON.toJSON(request));
        return creativeService.createCreative(request);
    }
}
