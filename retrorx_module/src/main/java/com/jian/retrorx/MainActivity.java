package com.jian.retrorx;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jian.retrorx.bean.BaseEntity;
import com.jian.retrorx.bean.GameInfo;
import com.jian.retrorx.bean.GameItem;
import com.jian.retrorx.bean.NewsInfo;
import com.jian.retrorx.bean.StockBean;
import com.jian.retrorx.constant.Constants;
import com.jian.retrorx.databinding.ActivityMainBinding;
import com.jian.retrorx.manager.NetWorkManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOnclickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getdata:
                getdata();
                break;
            case R.id.hasParam:
                getParamsData();
                break;
            case R.id.gsontrans:
                useGsonConver();
                break;
            case R.id.usepath:
                usePath();
                break;
            case R.id.querymap:
                useQuerymap();
                break;
            case R.id.post:
                post();
                break;
            case R.id.bodypost:
                bodyPost();
                break;
            case R.id.bodyjson:
                bodypostjson();
                break;
            case R.id.upload1:
                upload1();
                break;
            case R.id.upload2:
                upload2();
                break;
            case R.id.upload3:
                upload3();
                break;
            case R.id.upload4:
                upload4();
                break;
            case R.id.upload5:
                upload5();
                break;
            case R.id.upload6:
                upload6();
                break;
            case R.id.rxjava:
                rxjavaGetdata();
                break;
        }
    }

    /**
     * 无参请求
     */
    private void getdata() {
        Call<ResponseBody> call = NetWorkManager.getRequest(Constants.BASEURL).getbasil2style();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        Log.d("jianyb", "getdata onResponse: " + string);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("jianyb", "getdata onFailure: " + t.getMessage());
            }
        });
    }

    /**
     * 有参请求
     */
    private void getParamsData() {
        Call<ResponseBody> call = NetWorkManager.getRequest(Constants.BASEURL2)
                .getNewsInfo("12", "0", "android", "9724");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        Log.d("jianyb", "getParamsData onResponse: " + string);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("jianyb", "getParamsData onFailure: " + t.getMessage());
            }
        });
    }

    /**
     * 使用gson转化器
     */
    private void useGsonConver() {
        Call<BaseEntity<List<NewsInfo>>> call = NetWorkManager.getRequestwithGson(Constants.BASEURL2)
                .getNewsInfo2("12", "0", "android", "9724");
        call.enqueue(new Callback<BaseEntity<List<NewsInfo>>>() {
            @Override
            public void onResponse(Call<BaseEntity<List<NewsInfo>>> call, Response<BaseEntity<List<NewsInfo>>> response) {
                BaseEntity<List<NewsInfo>> body = response.body();
                List<NewsInfo> list = body.getList();
            }

            @Override
            public void onFailure(Call<BaseEntity<List<NewsInfo>>> call, Throwable t) {

            }
        });
    }

    /**
     * 使用@PATH注解暂未url
     */
    private void usePath() {
        Call<BaseEntity<List<NewsInfo>>> call = NetWorkManager.getRequestwithGson(Constants.BASEURL2)
                .getNewsInfoPath("php", 12, 0, "android", 9724);
        call.enqueue(new Callback<BaseEntity<List<NewsInfo>>>() {
            @Override
            public void onResponse(Call<BaseEntity<List<NewsInfo>>> call, Response<BaseEntity<List<NewsInfo>>> response) {
                BaseEntity<List<NewsInfo>> body = response.body();
                List<NewsInfo> list = body.getList();
            }

            @Override
            public void onFailure(Call<BaseEntity<List<NewsInfo>>> call, Throwable t) {

            }
        });
    }

    /**
     * @Querymap传递多个参数
     */
    private void useQuerymap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "12");
        map.put("page", "0");
        map.put("plat", "android");
        map.put("version", "9724");
        Call<BaseEntity<List<NewsInfo>>> call = NetWorkManager.getRequestwithGson(Constants.BASEURL2).getNewsInfo3(map);
        call.enqueue(new Callback<BaseEntity<List<NewsInfo>>>() {
            @Override
            public void onResponse(Call<BaseEntity<List<NewsInfo>>> call, Response<BaseEntity<List<NewsInfo>>> response) {
                BaseEntity<List<NewsInfo>> body = response.body();
                List<NewsInfo> list = body.getList();
            }

            @Override
            public void onFailure(Call<BaseEntity<List<NewsInfo>>> call, Throwable t) {

            }
        });
    }

    /**
     * POST请求
     */
    private void post() {
        Call<GameInfo<GameItem>> call = NetWorkManager.getRequestwithGson(Constants.BASEURL3).getGameInfo("2", "1");
        call.enqueue(new Callback<GameInfo<GameItem>>() {
            @Override
            public void onResponse(Call<GameInfo<GameItem>> call, Response<GameInfo<GameItem>> response) {
                GameInfo<GameItem> body = response.body();
                List<GameItem> info = body.getInfo();
            }

            @Override
            public void onFailure(Call<GameInfo<GameItem>> call, Throwable t) {

            }
        });
    }

    /**
     * @FieldMap的post请求
     */
    private void bodyPost() {
        Map<String, String> map = new HashMap<>();
        map.put("platform", "2");
        map.put("page", "1");
        Call<GameInfo<GameItem>> call = NetWorkManager.getRequestwithGson(Constants.BASEURL3).getGameInfo(map);
        call.enqueue(new Callback<GameInfo<GameItem>>() {
            @Override
            public void onResponse(Call<GameInfo<GameItem>> call, Response<GameInfo<GameItem>> response) {
                GameInfo<GameItem> body = response.body();
                List<GameItem> info = body.getInfo();
            }

            @Override
            public void onFailure(Call<GameInfo<GameItem>> call, Throwable t) {

            }
        });
    }

    /**
     * @Body上传json。表单上传数据
     */
    private void bodypostjson() {
        String json = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        NetWorkManager.getRequestwithGson("")
                .getNewsInfoByBody("", requestBody)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    /**
     * RxJava+retrofit
     */
    private void rxjavaGetdata() {
        NetWorkManager.getRequest()
                .getNewsInfoByRxJava("12", "0", "android", "9724")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseEntity<List<NewsInfo>>>() {
                    @Override
                    public void accept(BaseEntity<List<NewsInfo>> listBaseEntity) throws Exception {
                        List<NewsInfo> list = listBaseEntity.getList();
                    }
                });
    }


    /**
     * RequestBody形式上传单张图片
     */
    private void upload1() {
        String imagePath = "";
        File file = new File(imagePath);

        RequestBody body = RequestBody.create(MediaType.parse("image/png"), file);

        NetWorkManager.getRequestwithGson("")
                .upload1(body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    /**
     * MultipartBody.Part形式上传单张图片
     */
    private void upload2() {
        String imagePath = "";
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("上传的key", file.getName(), requestBody);
        NetWorkManager.getRequestwithGson("")
                .upload2(part)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    /**
     * Map + RequestBody上传多张图片
     * ("file" + "\";filename=\""+test.png)解释：
     * 服务器解析到数据为   Content-Disposition: form-data; name="file"; filename="test.png"
     * file是key，filename=test.png是服务器获取到图片存储的名称
     */
    private void upload3() {
        List<File> files = new ArrayList<>();//代表图片文件集合
        Map<String, RequestBody> map = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
            map.put("file" + i + "\";filename=\"" + files.get(i).getName(), requestBody);
        }

        NetWorkManager.getRequestwithGson("")
                .upload3(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    /**
     * map+MultipartBody.Part上传多张图片
     */
    private void upload4() {
        Map<String, MultipartBody.Part> map = new HashMap<>();

        File file1 = new File("");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file1);
        MultipartBody.Part part1 = MultipartBody.Part.createFormData("上传的key1", file1.getName(), requestBody);
        map.put("上传的key1", part1);

        File file2 = new File("");
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/png"), file2);
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("上传的key2", file2.getName(), requestBody2);
        map.put("上传的key2", part2);
        NetWorkManager.getRequestwithGson("")
                .upload4(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    /**
     * 图文混传1
     */
    private void upload5() {
        List<File> files = new ArrayList<>();//代表图片文件集合
        Map<String, RequestBody> map = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
            map.put("file" + i + "\";filename=\"" + files.get(i).getName(), requestBody);
        }

        Map<String, String> map2 = new HashMap<>();
        map2.put("参数1", "username");
        map2.put("参数2", "passworld");
        map2.put("参数3", "man");

        NetWorkManager.getRequestwithGson("")
                .upload5(map2, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    /**
     * 图文混传1
     */
    private void upload6() {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody username = RequestBody.create(textType, "二傻子");
        RequestBody passworld = RequestBody.create(textType, "123456");

        File file = new File("");//图片
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("上传的key", file.getName(), requestBody);
        NetWorkManager.getRequestwithGson("")
                .upload6(username, passworld, part)
                .enqueue(new Callback<BaseEntity<NewsInfo>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<NewsInfo>> call, Response<BaseEntity<NewsInfo>> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseEntity<NewsInfo>> call, Throwable t) {

                    }
                });
    }

}
