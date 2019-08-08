package com.qianlong.libary_master.http;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp请求管理
 *
 * @Author [raozy]
 * @EMail [23938232@qq.com]
 * @CreateTime [2019/03/26 13:29]
 **/

public class OkHttpRequestManager implements IRequestManager {
    private static final String TAG = OkHttpRequestManager.class.getSimpleName();
    private OkHttpClient okHttpClient;
    private Handler handler;
    private static int CONNECT_TIMEOUT = 10, READ_TIMEOUT = 10;//连接和读取超时时间

    public static OkHttpRequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static OkHttpRequestManager getInstance(int timeout) {
        return new OkHttpRequestManager(timeout);
    }

    private static class SingletonHolder {
        private static final OkHttpRequestManager INSTANCE = new OkHttpRequestManager();
    }

    public OkHttpRequestManager() {
       /*
       ipv4地址优先ipv6
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.dns(new MyDns());*/
        okHttpClient = ProgressManager.getInstance()
                .with(new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS))
                .build();
    }

    public OkHttpRequestManager(int timeout) {
        if (timeout <= 0) {
            timeout = 10;
        }
        okHttpClient = ProgressManager.getInstance()
                .with(new OkHttpClient.Builder()
                        .connectTimeout(timeout, TimeUnit.SECONDS)
                        .readTimeout(timeout, TimeUnit.SECONDS))
                .build();
    }

    private void initHandler() {
        //在哪个线程创建该对象，则最后的请求结果将在该线程回调
        if (handler == null) {
            handler = new Handler();
        }
    }

    @Override
    public void get(String url, IRequestCallback requestCallback) {
        initHandler();
        Log.i(TAG, "Get--->url:" + url);
        Request request = new Request.Builder().url(url).get().build();
        if (url.startsWith("http") && url.endsWith(".apk")) {
            addDownloadCallBack(requestCallback, request, url);
        } else
            addCallBack(requestCallback, request);
    }

    @Override
    public void get(String url, final IRequestCallback requestCallback, boolean isMainThread) {
        Log.i(TAG, "Get--->url:" + url);
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                requestCallback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    requestCallback.onSuccess(json);
                } else {
                    requestCallback.onFailure(new IOException(response.message() + ",url=" + call.request().url().toString()));
                }
            }
        });
    }

    @Override
    public void post(String url, Map<String, String> params, IRequestCallback requestCallback) {
        initHandler();
        Log.i(TAG, "POST--->url:" + url);
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && !params.isEmpty())
            for (String key : params.keySet()) {
                Log.i(TAG, "params--->" + key + ":" + params.get(key));
                builder.add(key, params.get(key));
            }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        addCallBack(requestCallback, request);
    }

    @Override
    public void post1(String url, Map<String, String> params, IRequestCallback requestCallback) {
        initHandler();
        Log.i(TAG, "POST1--->url:" + url);
        String json = new Gson().toJson(params);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        addCallBack(requestCallback, request);
    }

    @Override
    public void postFile(String url, Map<String, String> params, File file, IRequestCallback requestCallback) {
        initHandler();
        Log.i(TAG, "POSTFILE--->url:" + url);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (String key : params.keySet()) {
            Log.i(TAG, "params--->" + key + ":" + params.get(key));
            builder.addFormDataPart(key, params.get(key));
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        builder.addFormDataPart("profileImage", file.getName(), fileBody);

        MultipartBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        addCallBack(requestCallback, request);
    }

    @Override
    public void postFile(String url, Map<String, String> params, byte[] filebytes, String name, IRequestCallback requestCallback) {
        initHandler();
        Log.i(TAG, "POSTFILE--->url:" + url);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (String key : params.keySet()) {
            Log.i(TAG, "params--->" + key + ":" + params.get(key));
            builder.addFormDataPart(key, params.get(key));
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), filebytes);
        builder.addFormDataPart("profileImage", name, fileBody);

        MultipartBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        addCallBack(requestCallback, request);
    }

    @Override
    public void cancleRequest() {
        if (getCall() != null) {
            getCall().cancel();
        }
    }

    @Override
    public void cancelRequestAll() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
    }

    @Override
    public void cancelRequestByTag(Object tag) {
        if (tag == null || okHttpClient == null) {
            return;
        }
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    private void addCallBack(final IRequestCallback requestCallback, Request request) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                Log.i(TAG, "onFailure--->" + e.toString());
                notifyMainUiFail(requestCallback, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String json = response.body().string();
                    Log.i(TAG, "resonse:" + json);
                    notifyMainUiSuccess(requestCallback, json);
                } else {
                    notifyMainUiFail(requestCallback, new IOException(response.message() + ",url=" + call.request().url().toString()));
                }
            }
        });
        setCall(call);
    }


    private void addDownloadCallBack(final IRequestCallback requestCallback, Request request, final String url) {
        Call call = okHttpClient.newCall(request);
        setCall(call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.i(TAG, "onFailure--->" + e.toString());
                notifyMainUiFail(requestCallback, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream is = null;
                    FileOutputStream fos = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    try {
                        is = response.body().byteStream();
                        File file = new File(Environment.getExternalStorageDirectory(), DownloadApkUtils.getApkNameFromUrl(url));
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        fos.flush();
                        notifyMainUiSuccess(requestCallback, "下载成功");
                    } catch (Exception e) {
                        Log.e(TAG, "onFailure--->" + e.toString());
                        notifyMainUiFail(requestCallback, e);
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                            Log.e(TAG, "addDownloadCallBack--->" + e.toString());
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                            Log.e(TAG, "addDownloadCallBack--->" + e.toString());
                        }
                    }
                } else {
                    notifyMainUiFail(requestCallback, new IOException(response.message() + ",url=" + call.request().url().toString()));
                }
            }
        });
    }

    private void notifyMainUiSuccess(final IRequestCallback requestCallback, final String response) {
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (requestCallback != null) {
                    requestCallback.onSuccess(response);
                }
            }
        });
    }

    private void notifyMainUiFail(final IRequestCallback requestCallback, final Throwable throwable) {
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (requestCallback != null) {
                    requestCallback.onFailure(throwable);
                }
            }
        });
    }

    private Call mCall;

    private void setCall(Call call) {
        mCall = call;
    }

    public Call getCall() {
        return mCall;
    }
}