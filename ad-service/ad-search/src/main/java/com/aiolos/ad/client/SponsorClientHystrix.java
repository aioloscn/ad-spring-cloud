package com.aiolos.ad.client;

import com.aiolos.ad.client.vo.AdPlan;
import com.aiolos.ad.client.vo.AdPlanGetRequest;
import com.aiolos.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-02-04 17:34
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {

        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
