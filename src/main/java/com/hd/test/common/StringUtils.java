package com.hd.test.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.UUID;

public class StringUtils {

    public static final char C_SPACE = CharUtil.SPACE;
    public static final char C_TAB = CharUtil.TAB;
    public static final char C_DOT = CharUtil.DOT;
    public static final char C_SLASH = CharUtil.SLASH;
    public static final char C_BACKSLASH = CharUtil.BACKSLASH;
    public static final char C_CR = CharUtil.CR;
    public static final char C_LF = CharUtil.LF;
    public static final char C_UNDERLINE = CharUtil.UNDERLINE;
    public static final char C_COMMA = CharUtil.COMMA;
    public static final char C_DELIM_START = CharUtil.DELIM_START;
    public static final char C_DELIM_END = CharUtil.DELIM_END;
    public static final char C_BRACKET_START = CharUtil.BRACKET_START;
    public static final char C_BRACKET_END = CharUtil.BRACKET_END;
    public static final char C_COLON = CharUtil.COLON;

    public static final String SPACE = " ";
    public static final String TAB = "	";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String NULL = "null";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String DASHED = "-";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String BRACKET_START = "[";
    public static final String BRACKET_END = "]";
    public static final String COLON = ":";

    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp;";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_APOS = "&apos;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";

    public static final String EMPTY_JSON = "{}";

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

    public static String base64Encode(String val, String charsetName) throws UnsupportedEncodingException {
        byte[] data = val.getBytes(charsetName);
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(CharSequence template, Object... params) {
        if (null == template) {
            return null;
        }
        if (CollectionUtils.isEmpty(params) || isBlank(template)) {
            return template.toString();
        }
        return format(template.toString(), params);
    }

    /**
     * 格式化字符串<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param strPattern 字符串模板
     * @param argArray   参数列表
     * @return 结果
     */
    public static String format(final String strPattern, final Object... argArray) {
        if (isBlank(strPattern) || CollectionUtils.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        // 初始化定义好的长度以获得更好的性能
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;// 记录已经处理到的位置
        int delimIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1) {// 剩余部分无占位符
                if (handledPosition == 0) { // 不带占位符的模板直接返回
                    return strPattern;
                }
                // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                sbuf.append(strPattern, handledPosition, strPatternLength);
                return sbuf.toString();
            }

            // 转义符
            if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH) {// 转义符
                if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH) {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
//                    sbuf.append(str(argArray[argIndex]) , "utf8");
                    handledPosition = delimIndex + 2;
                } else {
                    // 占位符被转义
                    argIndex--;
                    sbuf.append(strPattern, handledPosition, delimIndex - 1);
                    sbuf.append(C_DELIM_START);
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                sbuf.append(strPattern, handledPosition, delimIndex);
//                sbuf.append(str(argArray[argIndex]));
                handledPosition = delimIndex + 2;
            }
        }

        // append the characters following the last {} pair.
        // 加入最后一个占位符后所有的字符
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf.toString();
    }

    /**
     *
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (obj instanceof Collection) {
            return obj.toString();
        }

        return obj.toString();
    }

    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、""<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(CharSequence str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (false == isEmoji(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断是否为emoji表情符<br>
     *
     * @param c 字符
     * @return 是否为emoji
     * @since 4.0.8
     */
    public static boolean isEmoji(char c) {
        //noinspection ConstantConditions
        return false == ((c == 0x0) || //
                (c == 0x9) || //
                (c == 0xA) || //
                (c == 0xD) || //
                ((c >= 0x20) && (c <= 0xD7FF)) || //
                ((c >= 0xE000) && (c <= 0xFFFD)) || //
                ((c >= 0x100000) && (c <= 0x10FFFF)));
    }

    static class CharUtil {
        public static final char SPACE = ' ';
        public static final char TAB = '	';
        public static final char DOT = '.';
        public static final char SLASH = '/';
        public static final char BACKSLASH = '\\';
        public static final char CR = '\r';
        public static final char LF = '\n';
        public static final char UNDERLINE = '_';
        public static final char DASHED = '-';
        public static final char COMMA = ',';
        public static final char DELIM_START = '{';
        public static final char DELIM_END = '}';
        public static final char BRACKET_START = '[';
        public static final char BRACKET_END = ']';
        public static final char COLON = ':';
        public static final char DOUBLE_QUOTES = '"';
        public static final char SINGLE_QUOTE = '\'';
        public static final char AMP = '&';
    }
}
