package com.aiolos.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 广告位信息
 * @author Aiolos
 * @date 2019-02-19 22:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    // 广告位编码
    private String adSlotCode;

    // 广告位的流量类型
    private Integer positionType;

    // 宽
    private Integer width;

    // 高
    private Integer height;

    // 广告物料类型：图片，视频等等
    private List<Integer> type;

    // 最低出价
    private Integer minCpm;
}
