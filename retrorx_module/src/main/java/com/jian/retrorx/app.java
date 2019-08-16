package com.jian.retrorx;

import android.app.Application;

import com.jian.retrorx.manager.NetWorkManager;

public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init();
    }
}
