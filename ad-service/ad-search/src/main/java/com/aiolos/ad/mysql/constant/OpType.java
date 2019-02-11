package com.aiolos.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author Aiolos
 * @date 2019-02-09 15:12
 */
public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
