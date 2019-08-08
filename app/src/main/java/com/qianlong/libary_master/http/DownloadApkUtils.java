package com.qianlong.libary_master.http;

/**
 * Created by lm on 2017/11/27.
 */

public class DownloadApkUtils {

    /**
     * 从下载连接中解析出文件名
     *
     * @param downloadUrl
     * @return
     */
    public static String getApkNameFromUrl(String downloadUrl) {
        return downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
    }

}
