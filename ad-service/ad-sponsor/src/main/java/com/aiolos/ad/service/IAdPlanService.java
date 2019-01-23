package com.aiolos.ad.service;

import com.aiolos.ad.entity.AdPlan;
import com.aiolos.ad.exception.AdException;
import com.aiolos.ad.vo.AdPlanGetRequest;
import com.aiolos.ad.vo.AdPlanRequest;
import com.aiolos.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-01-23 22:07
 */
public interface IAdPlanService {

    /**
     * 创建推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
