package com.ziwenl.library.http;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * PackageName : com.ziwenl.library.http
 * Author : Ziwen Lan
 * Date : 2019/2/26
 * Time : 10:24
 * Introduction :
 */
public class Http {
    private final static long DEFAULT_TIMEOUT = 30;
    public static String baseUrl = "https://shop.freshlejia.com/apitest/";
    public static Http http;
    private Retrofit mRetrofit;

    public static void initHttp(Context context) {
        http = new Http(context);
    }

    private Http(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestLogInterceptor())
                .addInterceptor(new RequestHeaderInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T createApi(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }
}
