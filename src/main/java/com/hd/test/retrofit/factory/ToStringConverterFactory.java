package com.hd.test.retrofit.factory;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 该类为演示自定义转换类
 *
 * 为了防止下列异常 而 String 转换 json 也会报错   ScalarsConverterFactory 可以转换java所有基本类型 String 自然也可以转
 * java.lang.IllegalArgumentException: Unable to create converter for class java.lang.String
 * java.lang.IllegalArgumentException: Could not locate ResponseBody converter for class java.lang.String.
 */
public class ToStringConverterFactory extends Converter.Factory {

    /**
     * 媒介类型
     */
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return (Converter<ResponseBody, String>) value -> value.string();
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return (Converter<String, RequestBody>) value -> RequestBody.create(MEDIA_TYPE, value);
        }
        return null;
    }
}
