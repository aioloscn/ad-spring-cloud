package com.aiolos.ad.constant;

import lombok.Getter;

/**
 * @author Aiolos
 * @date 2019-01-21 15:01
 */
@Getter
public enum CreativeType {

    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
