package com.aiolos.ad.client;

import com.aiolos.ad.client.vo.AdPlan;
import com.aiolos.ad.client.vo.AdPlanGetRequest;
import com.aiolos.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-02-04 17:09
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)     // 一旦这个服务不可用就服务降级
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
