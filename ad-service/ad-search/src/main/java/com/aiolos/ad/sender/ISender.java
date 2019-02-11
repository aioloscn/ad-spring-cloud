package com.aiolos.ad.sender;

import com.aiolos.ad.mysql.dto.MySqlRowData;

/**
 * @author Aiolos
 * @date 2019-02-11 16:34
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
