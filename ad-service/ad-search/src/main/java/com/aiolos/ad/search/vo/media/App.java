package com.aiolos.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用
 * @author Aiolos
 * @date 2019-02-19 22:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    // 应用编码
    private String appCode;

    // 应用名称
    private String appName;

    // 应用包名
    private String packageName;

    // 应用请求的页面的名称
    private String activityName;
}
