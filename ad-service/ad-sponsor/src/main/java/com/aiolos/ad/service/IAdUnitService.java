package com.aiolos.ad.service;

import com.aiolos.ad.exception.AdException;
import com.aiolos.ad.vo.*;

/**
 * @author Aiolos
 * @date 2019-01-24 13:12
 */
public interface IAdUnitService {

    /**
     * 创建推广单元
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 创建推广单元关键字
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * 创建推广单元兴趣
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 创建推广单元地域
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;
}
