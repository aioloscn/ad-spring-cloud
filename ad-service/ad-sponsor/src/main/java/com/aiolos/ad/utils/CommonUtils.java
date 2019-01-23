package com.aiolos.ad.utils;

import com.aiolos.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @author Aiolos
 * @date 2019-01-23 22:00
 */
public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value) {

        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString) throws AdException {

        try {
            return DateUtils.parseDate(dateString, parsePatterns);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }
}
