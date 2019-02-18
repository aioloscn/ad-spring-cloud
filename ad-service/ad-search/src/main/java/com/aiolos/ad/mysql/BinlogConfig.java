package com.aiolos.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Aiolos
 * @date 2019-02-18 16:15
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")       // 实现配置文件到java对象的转换
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {

    private String host;
    private Integer post;
    private String username;
    private String password;

    private String binlogName;
    private Long position;
}
