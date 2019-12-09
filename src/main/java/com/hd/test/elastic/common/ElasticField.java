package com.hd.test.elastic.common;

import java.lang.annotation.*;

/**
 * Elastic的字段属性
 *
 * @author Sands
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ElasticField {

    /**
     * 当前属性对应的列名(数据库字段名)
     */
    String value() default "";

    /**
     * Column的数据字段类型枚举
     */
    ElasticFieldTypeEnum type();

    /**
     * 是否进行索引
     */
    boolean index() default true;

}
