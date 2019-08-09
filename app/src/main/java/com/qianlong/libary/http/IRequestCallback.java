package com.qianlong.libary.http;

/**
 * @author:gkh
 * @date: 2017-05-24 13:43
 */

public interface IRequestCallback {
    void onSuccess(String response);
    void onFailure(Throwable throwable);
}
