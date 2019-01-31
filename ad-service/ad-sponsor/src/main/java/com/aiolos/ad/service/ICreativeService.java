package com.aiolos.ad.service;

import com.aiolos.ad.vo.CreativeRequest;
import com.aiolos.ad.vo.CreativeResponse;

/**
 * @author Aiolos
 * @date 2019-01-31 13:18
 */
public interface ICreativeService {

    /**
     * 创建创意
     * @param request
     * @return
     */
    CreativeResponse createCreative(CreativeRequest request);
}
