package com.aiolos.ad.mysql.listener;

import com.aiolos.ad.mysql.constant.Constant;
import com.aiolos.ad.mysql.constant.OpType;
import com.aiolos.ad.mysql.dto.BinlogRowData;
import com.aiolos.ad.mysql.dto.MySqlRowData;
import com.aiolos.ad.mysql.dto.TableTemplate;
import com.aiolos.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aiolos
 * @date 2019-02-11 16:35
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    @Resource(name = "indexSender")
    private ISender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    /**
     * 给各个数据表注册处理器
     * bean初始化后立即注册
     */
    @Override
    @PostConstruct
    public void register() {

        log.info("IncrementListener register db and table info");
        // k：table，v：database
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    /**
     * 将BinlogRowData转换成MySqlRowData，然后投递出去
     * @param eventData
     */
    @Override
    public void onEvent(BinlogRowData eventData) {

        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();

        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> afterMap : eventData.getAfter()) {

            Map<String, String> _afterMap = new HashMap<>();

            for (Map.Entry<String, String> entry : afterMap.entrySet()) {

                String colName = entry.getKey();
                String colValue = entry.getValue();

                _afterMap.put(colName, colValue);
            }

            rowData.getFieldValueMap().add(_afterMap);
        }

        sender.sender(rowData);
    }
}
