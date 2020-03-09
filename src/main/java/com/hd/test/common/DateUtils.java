package com.hd.test.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.*;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

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

    //endregion

    /**
     * 常用的日期字符串的时间格式的Pattern
     */
    private static final List<DateFormatPattern> SIMPLE_DATE_FORMAT_PATTERNS;

    /**
     * 日期字符串的时间格式的Pattern
     */
    private static final List<DateFormatPattern> DATE_FORMAT_PATTERNS;

    static {
        SIMPLE_DATE_FORMAT_PATTERNS = new ArrayList<>();
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(DATE_TIME_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(DATE_TIME_MS_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[+]\\d{4}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_MS_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}[+]\\d{4}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_MS_Z_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}[Z]$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_EX_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[+]\\d{2}[:]\\d{2}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_EX_MS_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}[+]\\d{2}[:]\\d{2}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_EX_MS_LONG_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{7}[+]\\d{2}[:]\\d{2}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern(DATE_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd'T'HH:mm:ss",
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}$")));
        SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd HH:mm",
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$")));
//            SIMPLE_DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy/MM/dd HH:mm:ss",
//                    Pattern.compile("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$")));

        DATE_FORMAT_PATTERNS = new ArrayList<>();
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(DATE_TIME_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(DATE_TIME_MS_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[+]\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_MS_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}[+]\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_MS_Z_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}[Z]$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_EX_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[+]\\d{2}[:]\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_EX_MS_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}[+]\\d{2}[:]\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(UTC_DATE_TIME_EX_MS_LONG_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{7}[+]\\d{2}[:]\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern(DATE_FORMAT,
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd'T'HH:mm:ss",
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd HH:mm",
                Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy/MM/dd HH:mm:ss",
                Pattern.compile("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
//            DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd'T'HH:mm:ss Z",
//                    Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}\\s[+]\\d{4}$")));
//            DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy-MM-dd'T'HH:mm:ss.SSS Z",
//                    Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}[T]\\d{1,2}:\\d{2}:\\d{2}[.]\\d{3}\\s[+]\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyyMMdd",
                Pattern.compile("^\\d{8}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd-MM-yyyy",
                Pattern.compile("^\\d{1,2}-\\d{1,2}-\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("MM/dd/yyyy",
                Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy/MM/dd",
                Pattern.compile("^\\d{4}/\\d{1,2}/\\d{1,2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd MMM yyyy",
                Pattern.compile("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd MMMM yyyy",
                Pattern.compile("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyyMMddHHmm",
                Pattern.compile("^\\d{12}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyyMMdd HHmm",
                Pattern.compile("^\\d{8}\\s\\d{4}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd-MM-yyyy HH:mm",
                Pattern.compile("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("MM/dd/yyyy HH:mm",
                Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyy/MM/dd HH:mm",
                Pattern.compile("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd MMM yyyy HH:mm",
                Pattern.compile("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd MMMM yyyy HH:mm",
                Pattern.compile("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyyMMddHHmmss",
                Pattern.compile("^\\d{14}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("yyyyMMdd HHmmss",
                Pattern.compile("^\\d{8}\\s\\d{6}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd-MM-yyyy HH:mm:ss",
                Pattern.compile("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("MM/dd/yyyy HH:mm:ss",
                Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd MMM yyyy HH:mm:ss",
                Pattern.compile("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd MMMM yyyy HH:mm:ss",
                Pattern.compile("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
        //29 一月 2017 18:08:43
        //29 十二月 2017 18:08:43
        DATE_FORMAT_PATTERNS.add(new DateFormatPattern("dd MMM yyyy HH:mm:ss",
                Pattern.compile("^\\d{1,2}\\s[\\u4e00-\\u9fa5]{2,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$")));
    }

    /**
     * 获取当前机器的时间
     *
     * @return result
     */
    @NotNull
    public static Date now() {
        return new Date();
    }

    /**
     * 获取当前utc的时间
     *
     * @return result
     */
    @NotNull
    public static Date utcNow() {
        //1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

    /**
     * 时分秒转换成时间
     */
    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = toCalendar(new Date());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 时分秒转换成时间
     */
    public static Date getDate(long milliSecond) {
        Calendar calendar = toCalendar(new Date());
        calendar.setTimeInMillis(milliSecond);
        return calendar.getTime();
    }

    /**
     * 本地时间转换成utc的时间
     */
    @NotNull
    public static Date localToUtc(Date date) {
        //1、取得本地时间：
        Calendar cal = toCalendar(date);
        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

    /**
     * 判断是否与当前时间相比较已经过期了
     *
     * @param timestamp  时间戳
     * @param expirySpan 过期间隔时间，秒
     */
    public static boolean isExpired(Long timestamp, long expirySpan) {
        return isExpired(timestamp, getTime(), expirySpan);
    }

    public static long getTime() {
        return System.currentTimeMillis();  //使用 currentTimeMillis，防止一些意外的错误
    }

    /**
     * 判断是否已经过期了
     *
     * @param timestamp  时间戳
     * @param now        当前时间
     * @param expirySpan 过期间隔时间，秒
     */
    public static boolean isExpired(Long timestamp, long now, long expirySpan) {
        if (timestamp != null) {
            return (now - timestamp) > (expirySpan * 1000);
        }
        return true;
    }

    /**
     * utc时间转换成本地的时间
     */
    @NotNull
    public static Date utcToLocal(Date date) {
        //1、取得本地时间：
        Calendar cal = toCalendar(date);
        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        //4、从UTC时间里加上这些差量，即可以取得本地时间：
        cal.add(Calendar.MILLISECOND, zoneOffset + dstOffset);
        return cal.getTime();
    }

    public static Date today() {
        return today(now());
    }

    public static Date today(Date date) {
        Calendar calendar = toCalendar(date);
        //时分秒清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 明天的开始时间（例如当前为2017年2月10日，那么返回值为：2017年2月11日）
     */
    public static Date tomorrow() {
        return tomorrow(now());
    }

    /**
     * 明天的开始时间（例如当前为2017年2月10日，那么返回值为：2017年2月11日）
     */
    public static Date tomorrow(Date date) {
        return today(addDays(date, 1));
    }

    /**
     * 今月的开始时间（例如当前为1月10日，那么返回值为：1月1日）
     */
    public static Date month() {
        return month(now());
    }

    /**
     * 今月的开始时间（例如当前为1月10日，那么返回值为：1月1日）
     */
    public static Date month(Date date) {
        Calendar calendar = toCalendar(date);
        //日置1
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //时分秒清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 下月的开始时间（例如当前为1月10日，那么返回值为：2月1日）
     */
    public static Date nextMonth() {
        return nextMonth(now());
    }

    /**
     * 下月的开始时间（例如当前为1月10日，那么返回值为：2月1日）
     */
    public static Date nextMonth(Date date) {
        return month(addMonths(date, 1));
    }

    /**
     * 下月的开始时间（例如当前为2月10日，那么返回值为：1月1日）
     */
    public static Date lastMonth() {
        return lastMonth(now());
    }

    /**
     * 下月的开始时间（例如当前为2月10日，那么返回值为：1月1日）
     */
    public static Date lastMonth(Date date) {
        return month(addMonths(date, -1));
    }

    /**
     * 今年的开始时间（例如当前为2017年2月10日，那么返回值为：2017年1月1日）
     */
    public static Date year() {
        return year(now());
    }

    /**
     * 今年的开始时间（例如当前为2017年2月10日，那么返回值为：2017年1月1日）
     */
    public static Date year(Date date) {
        Calendar calendar = toCalendar(date);
        //月置1
        calendar.set(Calendar.MONTH, 0);
        //日置1
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //时分秒清零
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 明年的开始时间（例如当前为2017年2月10日，那么返回值为：2018年1月1日）
     */
    public static Date nextYear() {
        return nextYear(now());
    }

    /**
     * 明年的开始时间（例如当前为2017年2月10日，那么返回值为：2018年1月1日）
     */
    public static Date nextYear(Date date) {
        return year(addYears(date, 1));
    }


    /**
     * 时分秒转换成时间
     */
    public static Date hmsToDate(Date date, String hms) {
        String[] split = StringUtils.split(hms, ":");
        if (split.length == 1) {
            return hmsToDate(date, Integer.parseInt(split[0]), 0, 0);
        } else if (split.length == 2) {
            return hmsToDate(date, Integer.parseInt(split[0]), Integer.parseInt(split[1]), 0);
        } else {
            String sec = split[2];
            String[] secAndMilli = StringUtils.split(sec, ".");
            if (secAndMilli.length == 1) {
                return hmsToDate(date, Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(sec));
            } else {
                //支持毫秒的
                return hmsToDate(date, Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(secAndMilli[0]), Integer.parseInt(secAndMilli[1]));
            }
        }
    }

    /**
     * 时分秒转换成时间
     */
    public static Date hmsToDate(Date date, int hour, int minute, int second) {
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 时分秒转换成时间
     */
    public static Date hmsToDate(Date date, int hour, int minute, int second, int millisecond) {
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    public static String hms(int hour, int minute, int second) {
        Calendar calendar = toCalendar(now());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return format(calendar.getTime(), "HH:mm:ss");
    }

    /**
     * 判断时间是否在 hmsStart 与 hmsEnd区间内，hmsStart 与 hmsEnd的区间是可以跨天的，例如：23:00:00 ~ 02:00:00
     *
     * @param date     时间
     * @param hmsStart 时分秒的时间：HH:mm:ss
     * @param hmsEnd   时分秒的时间：HH:mm:ss
     */
    public static boolean isInHms(Date date, String hmsStart, String hmsEnd) {
        Date startDate = hmsToDate(date, hmsStart);
        Date endDate = hmsToDate(date, hmsEnd);

        return isInHms(date, startDate, endDate);
    }

    /**
     * 判断时间是否在 hmsStart 与 hmsEnd区间内，hmsStart 与 hmsEnd的区间是可以跨天的，例如：23:00:00 ~ 02:00:00
     *
     * @param date      时间
     * @param startDate 时分秒的时间：HH:mm:ss
     * @param endDate   时分秒的时间：HH:mm:ss
     */
    public static boolean isInHms(Date date, Date startDate, Date endDate) {
        //start 大于 end，就是说明是跨天的时间
        if (isGreaterThan(startDate, endDate)) {
            if (isGreaterThan(date, endDate)) {
                return isGreaterThanOrEqual(date, startDate);    //例如hmsStart为:22:00, hmsEnd为:10:00，而alarmTime为23:00
            } else {
                return isLessThanOrEqual(date, endDate);    //例如hmsStart为:22:00, hmsEnd为:10:00，而alarmTime为07:00
            }
        } else {
            return isGreaterThanOrEqual(date, startDate) && isLessThanOrEqual(date, endDate);
        }
    }


    //<editor-fold desc="get date">

    public static int getYear(Date date) {
        return toCalendar(date).get(Calendar.YEAR);
    }

    public static int getYear(Calendar date) {
        return date.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        return toCalendar(date).get(Calendar.MONTH);
    }

    public static int getMonth(Calendar date) {
        return date.get(Calendar.MONTH);
    }

    public static int getDay(Date date) {
        return toCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    public static int getDay(Calendar date) {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        return toCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    public static int getHour(Calendar date) {
        return date.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        return toCalendar(date).get(Calendar.MINUTE);
    }

    public static int getMinute(Calendar date) {
        return date.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        return toCalendar(date).get(Calendar.SECOND);
    }

    public static int getSecond(Calendar date) {
        return date.get(Calendar.SECOND);
    }

    public static int getMillisecond(Date date) {
        return toCalendar(date).get(Calendar.MILLISECOND);
    }

    public static int getMillisecond(Calendar date) {
        return date.get(Calendar.MILLISECOND);
    }

    /**
     * 0 - 6 (0：星期天，6：星期六)
     */
    public static int getWeek(Date date) {
        return getWeek(toCalendar(date));
    }

    /**
     * 0 - 6 (0：星期天，6：星期六)
     */
    public static int getWeek(Calendar date) {
        return date.get(Calendar.DAY_OF_WEEK) - 1;
    }

    //</editor-fold>


    /**
     * 判断日期字符串的时间格式(常用的)
     *
     * @param dateString 日期字符串
     * @return key为format，value为格式的pattern
     */
    @Nullable
    public static DateFormatPattern getSimpleDatePattern(String dateString) {
        for (DateFormatPattern m : SIMPLE_DATE_FORMAT_PATTERNS) {
            if (m.getPattern().matcher(dateString).matches()) {
                return m;
            }
        }
        return null;
    }

    /**
     * 判断日期字符串的时间格式
     *
     * @param dateString 日期字符串
     * @return key为format，value为格式的pattern
     */
    @Nullable
    public static DateFormatPattern getDatePattern(String dateString) {
        for (DateFormatPattern m : DATE_FORMAT_PATTERNS) {
            if (m.getPattern().matcher(dateString).matches()) {
                return m;
            }
        }
        return null;
    }

    /**
     * 使用了SimpleDateFormat进行format
     */
    @NotNull
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 使用了SimpleDateFormat进行format
     */
    @NotNull
    public static String format(Date date, String format, Locale locale) {
        return new SimpleDateFormat(format, locale).format(date);
    }

    /**
     * 使用了SimpleDateFormat进行format(可为null，null返回: "")
     */
    public static String formatSafe(Date date, String format) {
        return date == null ? "" : new SimpleDateFormat(format).format(date);
    }

    /**
     * 时间转Rfc822字符串
     *
     * @param date 日期
     * @return 结果
     */
    public static String formatToRfc(Date date) {
        return format(new Date(), "EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH) + " GMT";
    }

    //region toString


    /**
     * 时间转字符串，使用了SimpleDateFormat
     *
     * @param date   日期
     * @param format 格式（例如："yyyy-MM-dd HH:mm:ss"）
     * @return 结果
     */
    public static String toString(Date date, String format) {
        return date != null ? new SimpleDateFormat(format).format(date) : "";
    }

    /**
     * 时间转字符串，使用了SimpleDateFormat
     *
     * @param date   日期
     * @param format 格式（例如："yyyy-MM-dd HH:mm:ss"）
     * @param defVal 默认的值，如果date为null
     * @return 结果
     */
    public static String toString(Date date, String format, String defVal) {
        return date != null ? new SimpleDateFormat(format).format(date) : defVal;
    }

    /**
     * 使用了"yyyy-MM-dd HH:mm:ss"转为字符串
     *
     * @param date 日期
     * @return 结果
     */
    public static String toString(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

    /**
     * 使用了"yyyy-MM-dd HH:mm:ss" / "yyyy-MM-dd HH:mm:ss.SSS"转为字符串
     *
     * @param date  日期
     * @param useMs 是否带毫秒的
     * @return 结果
     */
    public static String toString(Date date, boolean useMs) {
        if (useMs) {
            return new SimpleDateFormat(DATE_TIME_MS_FORMAT).format(date);
        } else {
            return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
        }
    }

    /**
     * 使用了"yyyy-MM-dd HH:mm:ss" / "yyyy-MM-dd HH:mm:ss.SSS"转为字符串
     *
     * @param date   日期
     * @param useMs  是否带毫秒的
     * @param isLong 是否带时分秒的，true为"yyyy-MM-dd HH:mm:ss"，false为"yyyy-MM-dd"
     * @return 结果
     */
    public static String toString(Date date, boolean useMs, boolean isLong) {
        if (useMs) {
            return isLong ? new SimpleDateFormat(DATE_TIME_MS_FORMAT).format(date) : new SimpleDateFormat(DATE_FORMAT).format(date);
        } else {
            return isLong ? new SimpleDateFormat(DATE_TIME_FORMAT).format(date) : new SimpleDateFormat(DATE_FORMAT).format(date);
        }
    }

    //endregion


    /**
     * 使用了SimpleDateFormat进行parse
     */
    @NotNull
    public static Date parse(String dateVal, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(dateVal);
    }

    /**
     * 是否大于
     */
    public static boolean isGreaterThan(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    /**
     * 是否小于
     */
    public static boolean isLessThan(Date date1, Date date2) {
        return date1.compareTo(date2) < 0;
    }

    /**
     * 是否等于
     */
    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 是否等于
     */
    public static boolean isGreaterThanOrEqual(Date date1, Date date2) {
        return isEqual(date1, date2) || isGreaterThan(date1, date2);
    }

    /**
     * 是否等于
     */
    public static boolean isLessThanOrEqual(Date date1, Date date2) {
        return isEqual(date1, date2) || isLessThan(date1, date2);
    }

    /**
     * 时间比较
     *
     * @return EQUALS / GREATER / LESS
     */
    @NotNull
    public static CompareEnum compare(Date date1, Date date2) {
        int cmp = date1.compareTo(date2);
        if (cmp < 0) {
            return CompareEnum.LESS;
        } else if (cmp > 0) {
            return CompareEnum.GREATER;
        }

        return CompareEnum.EQUALS;
    }

    // Converters ---------------------------------------------------------------------------------

    /**
     * 转换为Calendar
     *
     * @param date
     * @return
     */
    @NotNull
    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 转换为Calendar，并且设置时区
     *
     * @param date
     * @param timeZone
     * @return
     */
    @NotNull
    public static Calendar toCalendar(Date date, TimeZone timeZone) {
        Calendar calendar = toCalendar(date);
        calendar.setTimeZone(timeZone);
        return calendar;
    }

    /**
     * 增加毫秒（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addMillis(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MILLISECOND, addVal);
        return c.getTime();
    }

    /**
     * 增加秒（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addSeconds(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, addVal);
        return c.getTime();
    }

    /**
     * 增加分钟（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addMinutes(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, addVal);
        return c.getTime();
    }

    /**
     * 增加小时（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addHours(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
//        c.add(Calendar.HOUR, addVal);
        c.add(Calendar.HOUR_OF_DAY, addVal);
        return c.getTime();
    }

    /**
     * 增加天数（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addDays(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, addVal);
        return c.getTime();
    }

    /**
     * 增加星期（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addWeeks(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_MONTH, addVal);
        return c.getTime();
    }

    /**
     * 增加月份（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addMonths(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, addVal);
        return c.getTime();
    }

    /**
     * 增加年（可负数为减）
     *
     * @param date
     * @param addVal
     * @return
     */
    @NotNull
    public static Date addYears(Date date, int addVal) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, addVal);
        return c.getTime();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的毫秒数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static long timeSpanMillis(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的秒数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static int timeSpanSeconds(Date date1, Date date2) {
        return Seconds.secondsBetween(new DateTime(date2), new DateTime(date1)).getSeconds();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的分钟数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static int timeSpanMinutes(Date date1, Date date2) {
        return Minutes.minutesBetween(new DateTime(date2), new DateTime(date1)).getMinutes();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的小时数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static int timeSpanHours(Date date1, Date date2) {
        return Hours.hoursBetween(new DateTime(date2), new DateTime(date1)).getHours();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的天数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static int timeSpanDays(Date date1, Date date2) {
        return Days.daysBetween(new DateTime(date2), new DateTime(date1)).getDays();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的星期数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static int timeSpanWeeks(Date date1, Date date2) {
        return Weeks.weeksBetween(new DateTime(date2), new DateTime(date1)).getWeeks();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的月数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static int timeSpanMonths(Date date1, Date date2) {
        return Months.monthsBetween(new DateTime(date2), new DateTime(date1)).getMonths();
    }

    /**
     * 时间1 与 时间2 的时间差，返回相差的年数（就相当于：date1 - date2）
     *
     * @param date1
     * @param date2
     * @return
     */
    @NotNull
    public static int timeSpanYears(Date date1, Date date2) {
        return Years.yearsBetween(new DateTime(date2), new DateTime(date1)).getYears();
    }

}
