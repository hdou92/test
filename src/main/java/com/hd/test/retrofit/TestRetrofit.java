package com.hd.test.retrofit;

import com.hd.test.entity.RestResult;
import com.hd.test.retrofit.factory.ToStringConverterFactory;
import okhttp3.OkHttpClient;
import org.springframework.boot.json.GsonJsonParser;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestRetrofit {


    public static void main(String[] args) {

        String url = "http://localhost:8888";

        Retrofit r;

        r = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(new ToStringConverterFactory())
//                .addCallAdapterFactory(Java8CallAdapterFactory.create())
//                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(url)
                .build();

//        r = RetrofitUtils.getRetrofit(url);

        TestService testService = r.create(TestService.class);
        Call<String> info = testService.getInfo();
        executeRest(info);
//        try {
//            Response<RestResult<String>> execute = info.execute();
//            RestResult<String> body = execute.body();
//            System.out.println(body.getData());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static <T> void executeRest(Call<T> call) {
        try {
            Response<T> execute = call.execute();
            if (execute.isSuccessful()) {
                T body = execute.body();
                System.out.println(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
