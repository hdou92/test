package com.hd.test.retrofit;

import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * @auther houdu
 * @date 2020/9/22
 */
public interface TestOrder {


    /**
     * 测试 post 没有参数
     *
     * @return
     */
    @POST("/order/b2c/tmall/pushOrder")
    Call<Object> pushOrder(@RequestBody String json);
}
