package com.aiolos.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地理位置信息
 * @author Aiolos
 * @date 2019-02-19 22:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    // 纬度
    private Float latitude;

    // 经度
    private Float longitude;

    // 省份
    private String province;

    // 城市
    private String city;
}
