package com.aiolos.ad.mysql.listener;

import com.aiolos.ad.mysql.dto.BinlogRowData;

/**
 * @author Aiolos
 * @date 2019-02-11 13:54
 */
public interface IListener {

    void register();

    void onEvent(BinlogRowData eventData);
}
