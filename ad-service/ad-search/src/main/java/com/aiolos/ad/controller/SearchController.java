package com.aiolos.ad.controller;

import com.aiolos.ad.annotation.IgnoreResponseAdvice;
import com.aiolos.ad.client.SponsorClient;
import com.aiolos.ad.client.vo.AdPlan;
import com.aiolos.ad.client.vo.AdPlanGetRequest;
import com.aiolos.ad.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-02-04 16:26
 */
@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request) {

        log.info("ad-search: getAdPlans -> {}", JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request) {

        log.info("ad-search: getAdPlansByRibbon -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request, CommonResponse.class).getBody();
    }
}
