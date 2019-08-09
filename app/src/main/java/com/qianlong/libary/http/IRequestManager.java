package com.qianlong.libary.http;

import java.io.File;
import java.util.Map;

/**
 * @author:gkh
 * @date: 2017-05-24 13:42
 */

public interface IRequestManager {
    void get(String url, IRequestCallback requestCallback);

    void get(String url, IRequestCallback requestCallback, boolean isMainThread);

    void post(String url, Map<String, String> params, IRequestCallback requestCallback);

    void post1(String url, Map<String, String> params, IRequestCallback requestCallback);

    void postFile(String url, Map<String, String> params, File file, IRequestCallback requestCallback);

    void postFile(String url, Map<String, String> params, byte[] filebytes, String name, IRequestCallback requestCallback);

    void cancleRequest();

    void cancelRequestAll();

    void cancelRequestByTag(Object tag);
}
