package com.jian.retrorx.manager;

import com.jian.retrorx.constant.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager mInstance = null;

    private Retrofit mRetrofit, mRetrofitGson, mRetrofitRx, mRetrofitRxGson;

    //添加统一的请求头
    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "add cookies here")
                                .build();
                        return chain.proceed(request);
                    }
                }).build();
        return httpClient;
    }

    private RetrofitManager() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASEURL)
                .build();

        mRetrofitGson = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitRx = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mRetrofitRxGson = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    public <T> T getService(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    public <T> T getServiceGson(Class<T> tClass){
        return mRetrofitGson.create(tClass);
    }

    public <T> T getServiceRx(Class<T> tClass) {
        return mRetrofitRx.create(tClass);
    }

    public <T> T getServieceRxGson(Class<T> tClass) {
        return mRetrofitRxGson.create(tClass);
    }
}
