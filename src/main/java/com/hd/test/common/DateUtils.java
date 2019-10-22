package com.hd.test.common;

/**
 * 日期时间帮助类
 *
 * @author Sands
 */
public class DateUtils {

    //region format

    /**
     * "yyyy-MM-dd"
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public static final String DATE_TIME_MS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * "yyyy-MM-dd'T'HH:mm:ssZ": Z为时区：0800
     */
    public static final String UTC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * "yyyy-MM-dd'T'HH:mm:ss.SSSZ"，Z为时区，0800 (2018-06-01T22:26:08.682+0800)
     */
    public static final String UTC_DATE_TIME_MS_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"，例如序列化为：(2018-06-01T22:26:08.682Z)
     */
    public static final String UTC_DATE_TIME_MS_Z_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * "yyyy-MM-dd'T'HH:mm:ssXXX"，XXX为时区，08:00 (2018-06-01T22:26:08+08:00)
     */
    public static final String UTC_DATE_TIME_EX_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    /**
     * "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"，XXX为时区，08:00 (2018-06-01T22:26:08.682+08:00)
     */
    public static final String UTC_DATE_TIME_EX_MS_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX"，例如：(2018-06-01T22:26:08.0000682+08:00)
     */
    public static final String UTC_DATE_TIME_EX_MS_LONG_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX";

    //endregion

    //region time zone

    /**
     * UTC时区（无夏令时）
     */
    public static final String UTC_ZONE = "UTC";
    /**
     * Asia/Shanghai时区（在夏令时中）
     */
    public static final String ASIA_SHANG_HAI_ZONE = "Asia/Shanghai";
    /**
     * GMT+8时区（无夏令时）
     */
    public static final String GMT8_ZONE = "GMT+8";


}
