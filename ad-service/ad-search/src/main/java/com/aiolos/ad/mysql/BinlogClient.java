package com.aiolos.ad.mysql;

import com.aiolos.ad.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Aiolos
 * @date 2019-02-18 16:22
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;

    private final BinlogConfig config;

    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    public void connect() {

        // 新建一个线程防止主线程hang起
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPost(),
                    config.getUsername(),
                    config.getPassword()
            );

            // 如果binlogName为空或者position等于-1都不要从任何位置开始监听，而是从当前位置开始监听
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {

                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            client.registerEventListener(listener);

            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 断开mysql监听
     */
    public void close() {

        try {

            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
