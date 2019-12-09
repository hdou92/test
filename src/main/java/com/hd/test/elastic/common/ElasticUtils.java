package com.hd.test.elastic.common;

import com.hd.test.common.*;
import io.searchbox.core.SearchResult;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Elastic帮助类
 *
 * @author Sands
 */
public class ElasticUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticUtils.class);
    private static final Object LOCK = new Object();
    private static final Map<Class, Map<Field, ElasticFieldInfo>> COLUMN_MAP = new HashMap<>();


    /**
     * 将数据类型中字段对应的Elastic属性转换成XContentBuilder
     *
     * @param clazz
     * @return
     */
    public static XContentBuilder toXContentBuilder(Class<?> clazz) throws IOException {
        Collection<ElasticFieldInfo> columnInfos = ElasticUtils.getFieldInfos(clazz).values();
        XContentBuilder mapping = jsonBuilder()
                //数据格式如下：
                //定义数据中字段的类型与索引（不设置分词等等）
                .startObject()
                .startObject("properties");  //设置字段，下面的message/time为数据字段

        for (ElasticFieldInfo fieldInfo : columnInfos) {
            mapping.startObject(fieldInfo.getName());
            //使字段不进行分词
            mapping.field("type", fieldInfo.getType());
            if (fieldInfo.isIndex()) {
                mapping.field("index", "true");
            } else {
                mapping.field("index", "false");
            }
            mapping.endObject();
        }

        mapping.endObject().endObject();
        return mapping;
    }

    /**
     * 获取数据类型中字段对应的Elastic属性
     */
    public static Map<Field, ElasticFieldInfo> getFieldInfos(Class<?> clazz) {
        return ThreadUtils.createSingleton(() -> COLUMN_MAP.get(clazz),
                LOCK, () -> buildMap(clazz), colMap -> COLUMN_MAP.put(clazz, colMap));
    }


    /**
     * SearchResult to list
     */
    public static <T> List<T> toList(SearchResult result, Class<T> clazz) {
        List<T> list = new ArrayList<>();

        if (result.isSucceeded()) {
            try {
                List<SearchResult.Hit<T, Void>> hits = result.getHits(clazz);
                if (CollectionUtils.isNotEmpty(hits)) {
                    for (SearchResult.Hit<T, Void> hit : hits) {
                        list.add(hit.source);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return list;
    }

    /**
     * SearchResult to page
     */
    public static <T> TwoValue<List<T>, Long> toListPage(SearchResult result, Class<T> clazz) {
        List<T> list = toList(result, clazz);
        long total = ObjectUtils.getObject(result.getTotal(), 0L);
        return new TwoValue<>(list, total);
    }

    private static Map<Field, ElasticFieldInfo> buildMap(final Class<?> clazz) {
        Map<Field, ElasticFieldInfo> colMap = new HashMap<>();
        //column map
        buildFiledToColumnMap(clazz, colMap);
        buildFiledToColumnMapByGetter(clazz, colMap);
        return colMap;
    }

    /**
     * 通过Filed建立属性名称到字段名称的映射
     */
    private static void buildFiledToColumnMap(Class<?> clazz, Map<Field, ElasticFieldInfo> mappings) {

        //使用doWithFields而不是doWithLocalFields，目的是能加载父类的私有字段，doWithFields会加载所有的字段包括父类的私有字段
        ReflectionUtils.doWithFields(clazz, (field) -> {
                    if (!field.getName().equals(ObjectUtils.OBJECT_THIS_NAME)) {
                        ElasticField column = field.getAnnotation(ElasticField.class);
                        if (column != null) {
                            if (Modifier.isStatic(field.getModifiers())) {
                                LOGGER.warn("[{}]注解不适用于静态方法:[{}]", ElasticField.class.toString(), field);
                                return;
                            }

                            field.setAccessible(true);

                            ElasticFieldInfo columnInfo = toFieldInfo(column, field);
                            mappings.put(field, columnInfo);

                        }
                    }
                }
        );
    }

    /**
     * 通过getter()建立属性名称到字段名称的映射
     */
    private static void buildFiledToColumnMapByGetter(Class<?> clazz, Map<Field, ElasticFieldInfo> mappings) {

        //使用doWithMethods而不是doWithLocalMethods，目的是加载父类的方法，doWithLocalMethods只会加载自身的方法，父类的会忽略的
        ReflectionUtils.doWithMethods(clazz, (method) -> {
                    ElasticField column = method.getAnnotation(ElasticField.class);
                    if (column != null) {
                        if (Modifier.isStatic(method.getModifiers())) {
                            LOGGER.warn("[{}]注解不适用于静态方法: [{}]", ElasticField.class.toString(), method);
                            return;
                        }
                        String mname = method.getName();
                        if (!(mname.startsWith("get") || mname.startsWith("is")) || method.getParameterTypes().length > 0) {
                            LOGGER.warn("[{}]注解只适用于getter方法,而非: [{}]方法", ElasticField.class.toString(), method);
                            return;
                        }
                        String fieldName = BeanUtils.findPropertyForMethod(method).getName();
                        Field field = ReflectionUtils.findField(clazz, fieldName);
                        field.setAccessible(true);

                        ElasticFieldInfo columnInfo = toFieldInfo(column, field);
                        mappings.put(field, columnInfo);

                    }
                }
        );

    }

    private static ElasticFieldInfo toFieldInfo(ElasticField column, Field field) {
        String val = column.value();
        ElasticFieldInfo elasticFieldInfo = new ElasticFieldInfo();
        elasticFieldInfo.setIndex(column.index());
        elasticFieldInfo.setAnnotation(column);
        elasticFieldInfo.setField(field);
        elasticFieldInfo.setName(StringUtils.isNotEmpty(val) ? val : field.getName());
        elasticFieldInfo.setType(column.type().value());
        return elasticFieldInfo;
    }

}
