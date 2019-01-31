package com.aiolos.ad.service.impl;

import com.aiolos.ad.dao.CreativeRepository;
import com.aiolos.ad.entity.AdCreative;
import com.aiolos.ad.service.ICreativeService;
import com.aiolos.ad.vo.CreativeRequest;
import com.aiolos.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aiolos
 * @date 2019-01-31 13:26
 */
@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {

        AdCreative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
