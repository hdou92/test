package com.hd.test.retrofit;

import com.hd.test.entity.RestResult;
import com.hd.test.retrofit.data.Info;
import com.hd.test.retrofit.data.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类演示 retrofit 发送请求
 */
public class TestRetrofit {


    public static void main(String[] args) {

        String url = "http://localhost:8888";

        Retrofit r = RetrofitUtils.getStringConverterRetrofit(url);
        TestService testService = r.create(TestService.class);
        Call<String> info = testService.testString();
        System.out.println("调用返回值为 String 的无参接口...");
        executeRest(info);

        Retrofit r1 = RetrofitUtils.getRetrofit(url);
        TestService testService1 = r1.create(TestService.class);
        Call<RestResult<String>> info1 = testService1.testApiString();
        System.out.println("调用返回值为 RestResult<String> 的无参接口...");
        executeRest(info1);

        Call<RestResult<Info<Test>>> info2 = testService1.testApiPostObject();
        System.out.println("调用返回值为 RestResult<Info<Test>> 的无参接口...");
        executeRest(info2);

        Call<RestResult<Info<Test>>> info3 = testService1.testApiParam("test");
        System.out.println("调用返回值为 RestResult<Info<Test>> 的有参 'test' 接口...");
        executeRest(info3);

        Map<String , Object> map = new HashMap<>();
        map.put("code","test code");
        map.put("count",123);
        Call<RestResult<Info<Test>>> info4 = testService1.testParamApi(map);
        System.out.println("调用返回值为 RestResult<Info<Test>> 的有参 Test.class 接口...");
        executeRest(info4);
    }

    private static <T> void executeRest(Call<T> call) {
        try {
            Response<T> execute = call.execute();
            if (execute.isSuccessful()) {
                T body = execute.body();
                System.out.println(body + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
