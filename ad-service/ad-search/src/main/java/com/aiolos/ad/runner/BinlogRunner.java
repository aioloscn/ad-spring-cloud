package com.aiolos.ad.runner;

import com.aiolos.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Aiolos
 * @date 2019-02-18 16:58
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    /**
     * springboot启动的时候run方法会一起运行
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        log.info("Coming in BinlogRunner...");
        // 应用程序启动的时候开启对binlog监听
        client.connect();
    }
}
