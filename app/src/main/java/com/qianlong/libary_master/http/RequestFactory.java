package com.qianlong.libary_master.http;

/**
 * @author:gkh
 * @date: 2017-05-24 13:45
 */

public class RequestFactory {
    public static IRequestManager getRequestManager() {
        return OkHttpRequestManager.getInstance();
    }

    public static IRequestManager getRequestManager(int timeout) {
        return OkHttpRequestManager.getInstance(timeout);
    }
}
