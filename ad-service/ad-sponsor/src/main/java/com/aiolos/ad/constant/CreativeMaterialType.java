package com.aiolos.ad.constant;

import lombok.Getter;

/**
 * @author Aiolos
 * @date 2019-01-21 15:03
 */
@Getter
public enum CreativeMaterialType {

    JPG(1, "jpg"),
    BMP(2, "bmp"),
    MP4(3, "mp4"),
    AVI(4, "avi"),
    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMaterialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
