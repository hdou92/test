package com.hd.test.common;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ModelUtils {

    /**
     * 属性的复制
     *
     * @param dataSrc  数据源
     * @param dataDest 目标对象
     */
    public static <T> T copyProperties(Object dataSrc, T dataDest) {
        BeanUtils.copyProperties(dataSrc, dataDest);
        return dataDest;
    }

    /**
     * object的字段值(包括基类的)转换为map
     *
     * @param obj object
     * @return map
     */
    public static Map<String, Object> objectToMap(final Object obj) {
        Map<String, Object> map = new HashMap<>();
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            if (!field.getName().equals(ObjectUtils.OBJECT_THIS_NAME)) {
                field.setAccessible(true);  //设置为可访问的
                map.put(field.getName(), field.get(obj));
            }
        });
        return map;
    }

    /**
     * object的字段值(包括基类的)转换为map，而且过滤null值的
     *
     * @param obj object
     * @return map
     */
    public static Map<String, Object> objectToMapAndRemoveNull(final Object obj) {
        Map<String, Object> map = new HashMap<>();
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            if (!field.getName().equals(ObjectUtils.OBJECT_THIS_NAME)) {
                field.setAccessible(true);  //设置为可访问的
                Object val = field.get(obj);
                if (val != null) {
                    map.put(field.getName(), val);
                }
            }
        });
        return map;
    }

    /**
     * 遍历实体类的field字段(包括基类的)
     *
     * @param clazz 实体类
     */
    public static void foreachFields(final Class<?> clazz, final Consumer<Field> doField) {
        ReflectionUtils.doWithFields(clazz, field -> {
            if (!field.getName().equals(ObjectUtils.OBJECT_THIS_NAME)) {
                field.setAccessible(true);  //设置为可访问的
                doField.accept(field);
            }
        });
    }


}
