package com.hd.test.common;

public class ObjectUtils {
    public static final String OBJECT_THIS_NAME = "this$0";

    public static <T> T getObject(T src, T def) {
        return src == null ? def : src;
    }

    public static <T> boolean isEquals(T val1, T val2) {
        if (val1 == null) {
            return val2 == null;
        }
        return val1.equals(val2);
    }

    public static <T> boolean isNotEquals(T val1, T val2) {
        return !isEquals(val1, val2);
    }

    public static <T> boolean isEqualsAndNotNull(T val1, T val2) {
        if ((val1 == null) || (val2 == null)) {
            return false;
        }
        return val1.equals(val2);
    }

    public static <T> boolean isNotEqualsAndNotNull(T val1, T val2) {
        if ((val1 == null) || (val2 == null)) {
            return false;
        }
        return !val1.equals(val2);
    }

    /**
     * 是否为值类型：byte / int / long / short / float / double
     */
    public static boolean isValueType(Object obj) {
        return obj instanceof Integer || obj instanceof Long || obj instanceof Short
                || obj instanceof Byte || obj instanceof Float || obj instanceof Double;
    }

    /**
     * 是否为值类型：bool / byte / int / long / short / float / double
     */
    public static boolean isValueAndBoolType(Object obj) {
        return isValueType(obj) || obj instanceof Boolean;
    }


    /**
     * 不为null与0
     */
    public static boolean isNotNullAndZero(Integer val) {
        return val != null && val > 0;
    }

    /**
     * 不为null与0
     */
    public static boolean isNotNullAndZero(Long val) {
        return val != null && val > 0;
    }

    /**
     * 不为null与0
     */
    public static boolean isNotNullAndZero(Short val) {
        return val != null && val > 0;
    }

    /**
     * 不为null与0
     */
    public static boolean isNotNullAndZero(Float val) {
        return val != null && val > 0;
    }

    /**
     * 不为null与0
     */
    public static boolean isNotNullAndZero(Double val) {
        return val != null && val > 0;
    }



    /**
     * 如果值为null返回false
     */
    public static <T extends Comparable<T>> boolean isValueEqual(T val, T cmp) {
        return val != null && cmp != null && val.compareTo(cmp) == 0;
    }

    /**
     * 如果值为null返回false
     */
    public static <T extends Comparable<T>> boolean isLessThanOrEqual(T val, T cmp) {
        return val != null && cmp != null && val.compareTo(cmp) <= 0;
    }

    /**
     * 如果值为null返回false
     */
    public static <T extends Comparable<T>> boolean isLessThan(T val, T cmp) {
        return val != null && cmp != null && val.compareTo(cmp) < 0;
    }

    /**
     * 如果值为null返回false
     */
    public static <T extends Comparable<T>> boolean isGreaterThanOrEqual(T val, T cmp) {
        return val != null && cmp != null && val.compareTo(cmp) >= 0;
    }

    /**
     * 如果值为null返回false
     */
    public static <T extends Comparable<T>> boolean isGreaterThan(T val, T cmp) {
        return val != null && cmp != null && val.compareTo(cmp) > 0;
    }


}
