package com.aiolos.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Aiolos
 * @date 2019-02-11 13:51
 */
@Data
public class BinlogRowData {

    private TableTemplate table;

    private EventType eventType;

    /**
     * 变更后的数据
     */
    private List<Map<String, String>> after;

    /**
     * 变更前的数据，目前不使用
     */
    private List<Map<String, String>> before;
}
