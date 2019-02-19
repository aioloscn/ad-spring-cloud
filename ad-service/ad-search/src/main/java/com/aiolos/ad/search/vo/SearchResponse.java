package com.aiolos.ad.search.vo;

import com.aiolos.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aiolos
 * @date 2019-02-19 22:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    // k：广告位编码
    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        // 展示监测url
        private List<String> showMonitorUrl = Arrays.asList("www.aiolosxhx.com", "www.aiolosxhx.com");
        // 点击监测url
        private List<String> clickMonitorUrl = Arrays.asList("www.aiolosxhx.com", "www.aiolosxhx.com");
    }

    public static Creative convert(CreativeObject object) {

        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }
}
