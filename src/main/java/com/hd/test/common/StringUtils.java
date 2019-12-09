package com.hd.test.common;

import java.util.UUID;

public class StringUtils {

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
        if (StringUtils.isEmpty(val1)) {
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
        if (StringUtils.isEmpty(val1) || StringUtils.isEmpty(val2)) {
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


    /**
     * 尾部添加字符串
     *
     * @param src       源字符串
     * @param addVal    要添加的内容
     * @param existsAdd 如果存在是否也要添加
     */
    public static String endAdd(String src, String addVal, boolean existsAdd) {
        if (existsAdd) {
            return src + addVal;
        } else {
            if (!src.endsWith(addVal)) {
                return src + addVal;
            }
        }

        return src;
    }

    /**
     * 头部添加字符串
     *
     * @param src       源字符串
     * @param addVal    要添加的内容
     * @param existsAdd 如果存在是否也要添加
     */
    public static String startAdd(String src, String addVal, boolean existsAdd) {
        if (existsAdd) {
            return addVal + src;
        } else {
            if (!src.startsWith(addVal)) {
                return addVal + src;
            }
        }

        return src;
    }

    /**
     * startsWith，val1/val2为Empty返回false
     */
    public static boolean isStartWith(String val1, String val2) {
        return isNotEmpty(val1) && isNotEmpty(val2) && val1.startsWith(val2);
    }

    /**
     * 将多个字符串连接在一起
     *
     * @param pathExt 连接符
     * @param paths
     * @return
     */
    public static String pathCombine(char pathExt, String... paths) {
        return doPathCombine(paths, pathExt);
    }

    static String doPathCombine(String[] paths, Character pathExt) {
        CheckUtils.checkNotNull(paths, "paths");
        if (paths.length < 2) {
            throw new IllegalArgumentException("参数paths至少有两个元素");
        }

        StringBuilder sb = new StringBuilder();
        String ext = pathExt.toString();
        sb.append(paths[0].endsWith(ext) ? paths[0] : paths[0] + ext);
        int i = 1;
        String str = null;
        for (; i < paths.length - 1; i++) {
            str = trimStart(paths[i], pathExt);
            sb.append(str.endsWith(ext) ? str : str + ext);
        }
        sb.append(trimStart(paths[i], pathExt));
        return sb.toString();
    }

    /**
     * 去除头部空字符
     *
     * @param str
     * @return
     */
    public static String trimStart(String str, char... trimChars) {
        if (isEmpty(str)) {
            return str;
        }

        return str.replaceAll("^[" + new String(trimChars) + "]+", "");
    }

    /**
     * 判断字符串为空/空白符
     *
     * @param value 源
     * @return 结果
     */
    public static boolean isWhiteSpace(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 异常信息转成字符串
     */
    public static String exceptionToString(Exception ex) {
        return throwableToString(ex, false);
    }

    /**
     * 异常信息转成字符串
     */
    public static String throwableToString(Throwable ex, boolean filterNull) {
        String msg = ex.getMessage();
        if (filterNull && isEmpty(msg)) {
            return "";
        }

        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(ex.getClass() + ": " + msg);
            sb.append("\r\n\tat ");
            boolean split = false;
            for (StackTraceElement st : stackTrace) {
                if (split) {
                    sb.append("\r\n\tat ");
                } else {
                    split = true;
                }
                sb.append(st.toString());
            }
            return sb.toString();
        }
        return "exception: " + msg;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * @param trimLine 中间无-分割
     */
    public static String getUUID(boolean trimLine) {
        if (trimLine) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        } else {
            return UUID.randomUUID().toString();
        }
    }

    /**
     * 依据驼峰原则格式化属性或者类名称，名称转换为小写，并且在驼峰处前一位插入下划线(不包括首位字符)(就是会将：UserName解析为：user_name)
     *
     * @param name
     * @return
     */
    public static String formatHumpToUnderline(String name) {
        CheckUtils.checkNotEmpty(name, "name");
        StringBuilder sb = new StringBuilder();
//        sb.deleteByWhere(0, sb.length());
        sb.append(Character.toLowerCase(name.charAt(0)));
        for (int i = 1; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i))) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(name.charAt(i)));
        }
        return sb.toString();
    }

}
