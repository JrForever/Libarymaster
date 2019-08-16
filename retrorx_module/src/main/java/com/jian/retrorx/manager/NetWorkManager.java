package com.jian.retrorx.manager;

import com.jian.retrorx.constant.ApiService;
import com.jian.retrorx.constant.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {
    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile ApiService request = null;

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要的对象和参数
     */
    public void init() {
        //初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        //初始化retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASEURL2)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getRequest(){
        if (request == null) {
            synchronized (ApiService.class) {
                request = retrofit.create(ApiService.class);
            }
        }
        return request;
    }

    public static ApiService getRequest(String baseurl) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl).build();
        return retrofit.create(ApiService.class);
    }

    /**
     * 使用Gson转换器
     */
    public static ApiService getRequestwithGson(String baseurl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }
}
