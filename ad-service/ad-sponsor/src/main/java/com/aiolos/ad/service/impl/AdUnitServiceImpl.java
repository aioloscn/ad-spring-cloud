package com.aiolos.ad.service.impl;

import com.aiolos.ad.constant.Constants;
import com.aiolos.ad.dao.AdPlanRepository;
import com.aiolos.ad.dao.AdUnitRepository;
import com.aiolos.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.aiolos.ad.dao.unit_condition.AdUnitItRepository;
import com.aiolos.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.aiolos.ad.entity.AdPlan;
import com.aiolos.ad.entity.AdUnit;
import com.aiolos.ad.entity.unit_condition.AdUnitDistrict;
import com.aiolos.ad.entity.unit_condition.AdUnitIt;
import com.aiolos.ad.entity.unit_condition.AdUnitKeyword;
import com.aiolos.ad.exception.AdException;
import com.aiolos.ad.service.IAdUnitService;
import com.aiolos.ad.vo.*;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Aiolos
 * @date 2019-01-24 13:13
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanRepository planRepository;

    private final AdUnitRepository unitRepository;

    private final AdUnitKeywordRepository unitKeywordRepository;

    private final AdUnitItRepository unitItRepository;

    private final AdUnitDistrictRepository unitDistrictRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository, AdUnitKeywordRepository unitKeywordRepository,
                             AdUnitItRepository unitItRepository, AdUnitDistrictRepository unitDistrictRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.unitDistrictRepository = unitDistrictRepository;
    }

    @Override
    @Transactional
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldAdUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        AdUnit newAdUnit = unitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget()));

        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }

    @Override
    @Transactional
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {

        List<Long> unitIds = request.getUnitKeywords().stream().map(AdUnitKeywordRequest.UnitKeyword::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        // 存放AdUnitKeyword的主键
        List<Long> ids = Collections.emptyList();
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {

            request.getUnitKeywords().forEach(i -> unitKeywords.add(new AdUnitKeyword(i.getUnitId(), i.getKeyword())));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
        }
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    @Transactional
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {

        List<Long> unitIds = request.getUnitIts().stream().map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitIt> unitIts = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getUnitIts())) {

            request.getUnitIts().forEach(i -> unitIts.add(new AdUnitIt(i.getUnitId(), i.getItTag())));
            ids = unitItRepository.saveAll(unitIts).stream().map(AdUnitIt::getId).collect(Collectors.toList());
        }
        return new AdUnitItResponse(ids);
    }

    @Override
    @Transactional
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {

        List<Long> unitIds = request.getUnitDistricts().stream().map(AdUnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getUnitDistricts())) {

            request.getUnitDistricts().forEach(i -> unitDistricts.add(new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())));
            ids = unitDistrictRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId).collect(Collectors.toList());
        }
        return new AdUnitDistrictResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {

        if (CollectionUtils.isEmpty(unitIds))
            return false;
        // 用hashSet对入参去重
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }
}
