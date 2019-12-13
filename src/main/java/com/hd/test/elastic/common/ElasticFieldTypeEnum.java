package com.hd.test.elastic.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hd.test.common.EnumUtils;
import com.hd.test.common.EnumValue;

import java.util.Map;

/**
 * Elastic Field的数据字段类型枚举
 *
 * @author Sands
 */
public enum ElasticFieldTypeEnum implements EnumValue<String> {
    /**
     * 进行分词的字符串类型
     */
    TEXT("text"),
    /**
     * 不进行分词的字符串类型
     */
    KEYWORD("keyword"),
    DATE("date"),
    INTEGER("integer"),
    BYTE("byte"),
    SHORT("short"),
    LONG("long"),
    FLOAT("float"),
    BOOLEAN("boolean"),
    ARRAY("array");

    ElasticFieldTypeEnum(String val) {
        this.val = val;
    }

    private static final Map<String, ElasticFieldTypeEnum> VALUE_MAP = EnumUtils.getValueMap(values());
    private String val;

    @Override
    public String value() {
        return this.val;
    }

    @JsonCreator
    public static ElasticFieldTypeEnum getEnum(String val) {
        return VALUE_MAP.get(val);
    }

}
