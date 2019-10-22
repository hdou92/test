package com.hd.test.retrofit;

import com.hd.test.entity.RestResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TestService {

    /**
     * 测试 get 没有参数
     *
     * @return
     */
//    @GET("testApi")
//    Call<RestResult<String>> getInfo();
    @GET("testApi")
    Call<String> getInfo();

//    /**
//     *  测试 post 没有参数
//     * @return
//     */
//    @POST("testApiPost")
//    Call<RestResult<String>> testPostNotParam();
}
