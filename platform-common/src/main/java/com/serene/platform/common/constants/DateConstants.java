package com.serene.platform.common.constants;

import lombok.experimental.UtilityClass;

/**
 * @Description: 日期常量类
 * @Author: bin.tong
 * @Date: 2024/5/13 16:07
 */
@UtilityClass
public class DateConstants {

    /**
     * 时间格式:年月日 时分秒
     */
    public final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

    /**
     * UTC格式
     */
    public final String DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    /**
     * 当天起始 00:00:00
     */
    public final String BEGIN_OF_DAY = "00:00:00";

    /**
     * 当天结束 23:59:59
     */
    public final String END_OF_DAY = "23:59:59";
}
