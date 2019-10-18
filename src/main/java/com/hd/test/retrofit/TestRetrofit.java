package com.hd.test.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

public class TestRetrofit {

    public void send() {

    }

    public static void main(String[] args) {
        String url = "127.0.0.1";
//        Retrofit r = new Retrofit.Builder()
//                .client(new OkHttpClient())
//                .baseUrl(url)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();

        System.out.println(convert(1));
    }

    public static long convert(long sourceDuration) {
        throw new Error();
    }
}
