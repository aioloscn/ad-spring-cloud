package com.aiolos.ad.handler;

import com.aiolos.ad.dump.table.*;
import com.aiolos.ad.index.DataTable;
import com.aiolos.ad.index.IndexAware;
import com.aiolos.ad.index.adplan.AdPlanIndex;
import com.aiolos.ad.index.adplan.AdPlanObject;
import com.aiolos.ad.index.adunit.AdUnitIndex;
import com.aiolos.ad.index.adunit.AdUnitObject;
import com.aiolos.ad.index.creative.CreativeIndex;
import com.aiolos.ad.index.creative.CreativeObject;
import com.aiolos.ad.index.creativeunit.CreativeUnitIndex;
import com.aiolos.ad.index.creativeunit.CreativeUnitObject;
import com.aiolos.ad.index.district.UnitDistrictIndex;
import com.aiolos.ad.index.interest.UnitItIndex;
import com.aiolos.ad.index.keyword.UnitKeywordIndex;
import com.aiolos.ad.mysql.constant.OpType;
import com.aiolos.ad.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 1.索引之间存在着层级的划分，也就是依赖关系的划分
 * 2.加载全量索引其实是增量索引"添加"的一种特殊实现
 * @author Aiolos
 * @date 2019-02-09 14:52
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType type) {

        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(), planTable.getUserId(), planTable.getPlanStatus(), planTable.getStartDate(), planTable.getEndDate()
        );

        handleBinlogEvent(DataTable.of(AdPlanIndex.class), planObject.getPlanId(), planObject, type);
    }

    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {

        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(), creativeTable.getName(), creativeTable.getType(), creativeTable.getMaterialType(),
                creativeTable.getHeight(), creativeTable.getWidth(), creativeTable.getAuditStatus(), creativeTable.getAdUrl()
        );

        handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeObject.getAdId(), creativeObject, type);
    }

    public static void handleLevel3(AdUnitTable unitTable, OpType type) {

        AdPlanObject planObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());

        if (null == planObject) {
            log.error("handleLevel3 found AdPlanObject error: {}", unitTable.getPlanId());
            return;
        }

        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(), unitTable.getUnitStatus(), unitTable.getPositionType(), unitTable.getPlanId(), planObject
        );

        handleBinlogEvent(DataTable.of(AdUnitIndex.class), unitObject.getUnitId(), unitObject, type);
    }

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("creativeUnit index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());

        if (null == unitObject || null == creativeObject) {
            log.error("AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }

        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(creativeUnitTable.getAdId(), creativeUnitTable.getUnitId());

        handleBinlogEvent(DataTable.of(CreativeUnitIndex.class), CommonUtils.stringConcat(
                creativeUnitObject.getAdId().toString(), creativeUnitObject.getUnitId().toString()
                ), creativeUnitObject, type
        );
    }

    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());

        if (unitObject == null) {
            log.error("AdUnitDistrictTable index error: {}", unitDistrictTable.getUnitId());
        }

        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(Collections.singleton(unitDistrictTable.getUnitId()));

        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    public static void handleLevel4(AdUnitItTable unitItTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("it index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());

        if (unitObject == null) {
            log.error("AdUnitItTable index error: {}", unitItTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(Collections.singleton(unitItTable.getUnitId()));

        handleBinlogEvent(DataTable.of(UnitItIndex.class), unitItTable.getItTag(), value, type);
    }

    public static void handleLevel4(AdUnitKeywordTable unitKeywordTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitKeywordTable.getUnitId());

        if (unitObject == null) {
            log.error("AdUnitKeywordTable index error: {}", unitKeywordTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(Collections.singleton(unitKeywordTable.getUnitId()));

        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class), unitKeywordTable.getKeyword(), value, type);
    }

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {

        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
