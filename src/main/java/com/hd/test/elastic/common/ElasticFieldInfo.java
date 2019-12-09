package com.hd.test.elastic.common;

import java.lang.reflect.Field;

/**
 * Elastic Column相关的信息
 *
 * @author Sands
 */
public class ElasticFieldInfo {
    private ElasticField annotation;
    private String name;
    private boolean index;
    private String type;
    private Field field;

    public ElasticField getAnnotation() {
        return annotation;
    }

    public void setAnnotation(ElasticField annotation) {
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ElasticFieldInfo{" +
                "annotation=" + annotation +
                ", name='" + name + '\'' +
                ", index=" + index +
                ", type='" + type + '\'' +
                ", field=" + field +
                '}';
    }
}
