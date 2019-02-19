package com.aiolos.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备信息
 * @author Aiolos
 * @date 2019-02-19 22:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    // 设备id
    private String deviceCode;

    // 局域网地址
    private String mac;

    private String ip;

    // 机型编码
    private String model;

    // 分辨率尺寸
    private String displaySize;

    // 屏幕尺寸
    private String screenSize;

    // 设备序列号
    private String serialName;
}
