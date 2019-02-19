package com.aiolos.ad.search.impl;

import com.aiolos.ad.index.DataTable;
import com.aiolos.ad.index.adunit.AdUnitIndex;
import com.aiolos.ad.search.ISearch;
import com.aiolos.ad.search.vo.SearchRequest;
import com.aiolos.ad.search.vo.SearchResponse;
import com.aiolos.ad.search.vo.feature.DistrictFeature;
import com.aiolos.ad.search.vo.feature.FeatureRelation;
import com.aiolos.ad.search.vo.feature.ItFeature;
import com.aiolos.ad.search.vo.feature.KeywordFeature;
import com.aiolos.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Aiolos
 * @date 2019-02-20 00:19
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    @Override
    public SearchResponse fetchAds(SearchRequest request) {

        // 请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        // 三个 Feature
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();

        FeatureRelation relation = request.getFeatureInfo().getRelation();

        // 构造相应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {

            Set<Long> targetUnitIdSet;

            // 根据流量类型获取初始AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
        }

        return null;
    }
}
