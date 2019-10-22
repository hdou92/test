package com.hd.test.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * json工具类，使用了jackson的
 *
 * @author Sands
 */
public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setDateFormat(new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT));
        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
//        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
//      // jackson 1.9 and before
//        MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // or jackson 2.0
        //@JsonIgnoreProperties(ignoreUnknown = true)，反序列化时忽略类中不存在的字段（默认时不忽略的，就是json中有不存在的字段就会抛异常的）
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    /**
     * 将对象转化为json
     *
     * @param obj 待转化的对象
     * @return 当转化发生异常时返回null
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }

        return MAPPER.writeValueAsString(obj);
    }

    /**
     * 将对象转化为json(throw RuntimeException)
     *
     * @param obj 待转化的对象
     * @return 当转化发生异常时返回null
     */
    public static String toJsonEx(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json转化为对象
     *
     * @param json  json对象
     * @param clazz 待转化的对象类型
     * @return 当转化发生异常时返回null
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        return MAPPER.readValue(json, clazz);
    }

    /**
     * 将json转化为对象(throw RuntimeException)
     *
     * @param json  json对象
     * @param clazz 待转化的对象类型
     * @return 当转化发生异常时返回null
     */
    public static <T> T fromJsonEx(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json转化为对象(throw RuntimeException)
     * @param json json对象
     * @return 当转化发生异常时返回null
     */
    public static <T> T fromJsonEx(String json) {
        if(StringUtils.isEmpty(json)){
            return null;
        }

        try {
            return MAPPER.readValue(json, new TypeReference<T>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json对象转化为集合类型
     *
     * @param json            json对象
     * @param collectionClazz 具体的集合类的class，如：ArrayList.class
     * @param clazz           集合内存放的对象的class
     */
//    @SuppressWarnings("rawtypes")
    public static <T> List<T> fromJsonToList(String json, Class<? extends Collection> collectionClazz,
                                             Class<T> clazz) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        return MAPPER.readValue(json, getCollectionType(MAPPER, collectionClazz, clazz));
    }

    /**
     * 将json对象转化为集合类型
     *
     * @param json            json对象
     * @param collectionClazz 具体的集合类的class，如：ArrayList.class
     * @param clazz           集合内存放的对象的class
     */
//    @SuppressWarnings("rawtypes")
    public static <T> List<T> fromJsonToListEx(String json, Class<? extends Collection> collectionClazz,
                                               Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return MAPPER.readValue(json, getCollectionType(MAPPER, collectionClazz, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json对象转化为集合类型
     *
     * @param json            json对象
     * @param collectionClazz 具体的集合类的class，如：ArrayList.class
     */
//    @SuppressWarnings("rawtypes")
    public static <TK, TV> Map<TK, TV> fromJsonToMap(String json, Class<? extends Map> collectionClazz,
                                                     Class<TK> keyClazz, Class<TV> valClazz) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        return MAPPER.readValue(json, getCollectionType(MAPPER, collectionClazz, keyClazz, valClazz));
    }

    /**
     * 将json对象转化为集合类型
     *
     * @param json            json对象
     * @param collectionClazz 具体的集合类的class，如：ArrayList.class
     */
//    @SuppressWarnings("rawtypes")
    public static <TK, TV> Map<TK, TV> fromJsonToMapEx(String json, Class<? extends Map> collectionClazz,
                                                       Class<TK> keyClazz, Class<TV> valClazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return MAPPER.readValue(json, getCollectionType(MAPPER, collectionClazz, keyClazz, valClazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


}
