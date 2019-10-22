package com.hd.test.common;

public class CheckUtils {
    public static void checkTrue(Boolean bval) {
        if (ObjectUtils.isNotEquals(bval, Boolean.valueOf(true))) {
            throw new IllegalArgumentException("checkTrue error, val: " + bval);
        }
    }

    public static void checkFalse(Boolean bval) {
        if (ObjectUtils.isNotEquals(bval, Boolean.valueOf(false))) {
            throw new IllegalArgumentException("checkTrue error, val: " + bval);
        }
    }

    public static void checkNotNull(Object obj, String paramName) {
        if (obj == null) {
            throw new NullPointerException(paramName);
        }
    }

    public static void checkNotEmpty(String val, String paramName, String msg) {
        if (StringUtils.isEmpty(val)) {
            throw new IllegalArgumentException((StringUtils.isEmpty(paramName) ? "" : new StringBuilder().append("[paramName=").append(paramName).append("]:").toString()) + msg);
        }
    }

    public static void checkNotEmpty(String val, String paramName) {
        checkNotEmpty(val, paramName, "can not be null or empty.");
    }

    public static void checkNotWhiteSpace(String val, String paramName, String msg) {
        if (StringUtils.isWhiteSpace(val)) {
            throw new IllegalArgumentException((StringUtils.isEmpty(paramName) ? "" : new StringBuilder().append("[paramName=").append(paramName).append("]:").toString()) + msg);
        }
    }

    public static void checkNotWhiteSpace(String val, String paramName) {
        checkNotWhiteSpace(val, paramName, "can not be null or white space.");
    }

    public static void checkEqual(Object val, Object cmpVal, String msg, String paramName) {
        if (ObjectUtils.isNotEquals(val, cmpVal)) {
            throw new IllegalArgumentException((StringUtils.isEmpty(paramName) ? "" : new StringBuilder().append("[paramName=").append(paramName).append("]:").toString()) + msg + ", " + val + " != " + cmpVal);
        }
    }

    public static void checkNotEqual(Object val, Object cmpVal, String msg, String paramName) {
        if (ObjectUtils.isEquals(val, cmpVal)) {
            throw new IllegalArgumentException((StringUtils.isEmpty(paramName) ? "" : new StringBuilder().append("[paramName=").append(paramName).append("]:").toString()) + msg + ", " + val + " == " + cmpVal);
        }
    }

}
