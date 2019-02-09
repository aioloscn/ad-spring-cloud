package com.aiolos.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
 * @author Aiolos
 * @date 2019-02-09 21:37
 */
public class BinlogServiceTest {

    public static void main(String[] args) throws Exception {

        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "aiolos1204285");
//        client.setBinlogFilename();
//        client.setBinlogPosition();
        // 从最新的binlog文件的最后一个位置开始监听
        client.registerEventListener(event -> {

            EventData data = event.getData();

            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update------------------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write-------------------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete------------------");
                System.out.println(data.toString());
            }
        });

        client.connect();
    }
}