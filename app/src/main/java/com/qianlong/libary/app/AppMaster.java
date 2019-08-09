package com.qianlong.libary.app;

import android.app.Application;

public class AppMaster extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LibApp.getInstance().init(this);
    }
}
