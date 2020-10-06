package com.aiolos.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Aiolos
 * @date 2019-02-09 21:37
 */
public class BinlogServiceTest {

//    Write-------------------
//    WriteRowsEventData{tableId=67, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
//      [10, 10, plan, 1, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019]
//    ]}

    @Value("${username}")
    private static String username;

    @Value("${password}")
    private static String password;

    public static void main(String[] args) throws Exception {

        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, username, password);
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
