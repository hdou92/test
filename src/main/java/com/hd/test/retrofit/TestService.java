package com.hd.test.retrofit;

import com.hd.test.entity.RestResult;
import com.hd.test.retrofit.data.Info;
import com.hd.test.retrofit.data.Test;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface TestService {

    /**
     * 测试 get 没有参数
     *
     * @return
     */
    @GET("testString")
    Call<String> testString();

    /**
     * 测试 get 没有参数
     *
     * @return
     */
    @GET("testApiString")
    Call<RestResult<String>> testApiString();

    /**
     * 测试 post 没有参数
     *
     * @return
     */
    @POST("testApiPostObject")
    Call<RestResult<Info<Test>>> testApiPostObject();

    /**
     * 测试 post 有参数
     *
     * @param name
     * @Field 必须要配合 @FormUrlEncoded 使用 不然会报错 或者 使用 @Query
     * @Field 只能配合 @POST 使用    @Query 可以在 @GET 中使用  也可以在 @POST 中使用
     * @QueryMap 只能配合 @GET 使用 不然会报错
     * @return
     */
    @POST("testParam")
    @FormUrlEncoded
    Call<RestResult<Info<Test>>> testApiParam(@Field("name") String name);

    /**
     * 测试 post 有参数
     * @FormUrlEncoded 必须加  不加会报错
     * @return
     */
    @POST("testParamApi")
    @FormUrlEncoded
    Call<RestResult<Info<Test>>> testParamApi(@FieldMap Map<String, Object> test);

}
