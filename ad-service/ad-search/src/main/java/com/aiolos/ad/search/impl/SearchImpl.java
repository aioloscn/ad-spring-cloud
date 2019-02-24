package com.aiolos.ad.search.impl;

import com.aiolos.ad.index.CommonStatus;
import com.aiolos.ad.index.DataTable;
import com.aiolos.ad.index.adunit.AdUnitIndex;
import com.aiolos.ad.index.adunit.AdUnitObject;
import com.aiolos.ad.index.creative.CreativeIndex;
import com.aiolos.ad.index.creative.CreativeObject;
import com.aiolos.ad.index.creativeunit.CreativeUnitIndex;
import com.aiolos.ad.index.district.UnitDistrictIndex;
import com.aiolos.ad.index.interest.UnitItIndex;
import com.aiolos.ad.index.keyword.UnitKeywordIndex;
import com.aiolos.ad.search.ISearch;
import com.aiolos.ad.search.vo.SearchRequest;
import com.aiolos.ad.search.vo.SearchResponse;
import com.aiolos.ad.search.vo.feature.DistrictFeature;
import com.aiolos.ad.search.vo.feature.FeatureRelation;
import com.aiolos.ad.search.vo.feature.ItFeature;
import com.aiolos.ad.search.vo.feature.KeywordFeature;
import com.aiolos.ad.search.vo.media.AdSlot;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Aiolos
 * @date 2019-02-20 00:19
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    public SearchResponse fallback(SearchRequest request, Throwable e) {
        return null;
    }

    // fetchAds抛出异常的时候就会执行fallback方法，通过@HystrixCommand中的@EnableCircuitBreaker实现回退
    // @EnableCircuitBreaker通过aop拦截所有的HystrixCommand注解的方法，将fetchAds方法扔到Hystrix线程池里边
    // 发生失败的时候通过反射调用fallback方法
    @Override
    @HystrixCommand(fallbackMethod = "fallback")    // 效率低
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

            if (relation == FeatureRelation.AND) {

                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {

                targetUnitIdSet = getORRelationUnitIds(adUnitIdSet, keywordFeature, districtFeature, itFeature);
            }

            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);

            // 根据状态过滤
            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            // 通过unitObjects获取他所关联到的所有创意的id
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            // 通过adIds获取到关联的创意对象
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);

            // 通过AdSlot实现对CreativeObject的过滤
            filterCreativeByAdSlot(creatives, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());

            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creatives));
        }

        log.info("fetchAds: {}-{}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet, KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature, ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIdSet))
            return Collections.emptySet();

        // 保存3份副本
        Set<Long> keywordUnitSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitSet = new HashSet<>(adUnitIdSet);

        // 过滤
        filterKeywordFeature(keywordUnitSet, keywordFeature);
        filterDistrictFeature(districtUnitSet, districtFeature);
        filterItTagFeature(itUnitSet, itFeature);

        // 返回过滤后的并集合
        return new HashSet<>(CollectionUtils.union(CollectionUtils.union(keywordUnitSet, districtUnitSet), itUnitSet));
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {

        if (CollectionUtils.isEmpty(adUnitIds))
            return;

        // 不为空才需要过滤
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {

            // 对adUnitIds执行for循环，adUnitId是每一个集合中的元素，如果不通过后面的匹配就从集合中移除掉
            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords()));
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {

        if (CollectionUtils.isEmpty(adUnitIds))
            return;

        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {

            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts()));
        }
    }

    private void filterItTagFeature(Collection<Long> adUnitIds, ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIds))
            return;

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {

            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts()));
        }
    }

    /**
     * 对状态的过滤
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }

        CollectionUtils.filter(unitObjects, object ->
            object.getUnitStatus().equals(status.getStatus()) &&
                    object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    /**
     * 根据广告位条件过滤创意信息
     * @param creatives
     * @param width
     * @param height
     * @param type
     */
    private void filterCreativeByAdSlot(List<CreativeObject> creatives, Integer width, Integer height, List<Integer> type) {

        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(creatives, creative ->
                creative.getAuditStatus().equals(CommonStatus.VALID.getStatus()) &&
                        creative.getWidth().equals(width) &&
                        creative.getHeight().equals(height) &&
                        type.contains(creative.getType())
        );
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives) {

        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }

        // 随机从list中取一个object
        CreativeObject randomObject = creatives.get(Math.abs(new Random().nextInt()) % creatives.size());

        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
