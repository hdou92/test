package com.hd.test.common;

public class ZStringUtils {

    /**
     * 字符串比较，如果都为null返回true
     *
     * @param val1 可null
     * @param val2 可null
     */
    public static boolean isEquals(String val1, String val2) {
        if (val1 == null) {
            return val2 == null;
        }

        return val1.equals(val2);
    }

    /**
     * 字符串比较，如果值为null/empty返回false
     *
     * @param val1 可null
     * @param val2 可null
     */
    public static boolean isEqualsAndNotEmpty(String val1, String val2) {
        if (ZStringUtils.isEmpty(val1)) {
            return false;
        }

        return val1.equals(val2);
    }

    /**
     * 字符串比较，如果值为null/empty返回false
     *
     * @param val1 可null
     * @param val2 可null
     */
    public static boolean isNotEqualsAndNotEmpty(String val1, String val2) {
        if (ZStringUtils.isEmpty(val1) || ZStringUtils.isEmpty(val2)) {
            return false;
        }

        return !val1.equals(val2);
    }

    /**
     * 判断字符串为空
     *
     * @param value 源
     * @return 结果
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 根据指定的字符分成两半，从结尾开始寻找，如果找不到，那么第二个字符串为null
     *
     * @param val
     * @param split
     * @return
     */
    public static TwoValue<String, String> endSplitTwo(String val, String split) {
        int idx = val.lastIndexOf(split);
        if (idx < 0) {
            return new TwoValue<>(val, null);
        } else {
            return new TwoValue<>(substring(val, 0, idx),
                    substring(val, idx + split.length()));
        }
    }

    /**
     * 获取子字符串
     *
     * @param source     源字符串
     * @param beginIndex 其实索引位
     * @param length     需要截取的长度
     * @return
     */
    public static String substring(String source, int beginIndex, int length) {
        return source.substring(beginIndex, beginIndex + length);
    }

    /**
     * 获取子字符串
     *
     * @param source     源字符串
     * @param beginIndex 其实索引位
     * @return
     */
    public static String substring(String source, int beginIndex) {
        return source.substring(beginIndex);
    }
}
