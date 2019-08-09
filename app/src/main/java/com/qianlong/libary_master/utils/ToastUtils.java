package com.qianlong.libary_master.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by lm on 2017/7/21.
 * <p>
 * 多次点击只展示一次toast
 */

public class ToastUtils {
    private static final String TAG = ToastUtils.class.getSimpleName();
    protected static Toast toast = null;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

//    public static void showToast(String tip) {
//        showToast(Utils.getApp(), tip);
//    }

    public static void showToast(Context context, String s) {
        WeakReference<Context> contextRef = new WeakReference<>(context.getApplicationContext());
        try {
            if (toast == null) {
                toast = Toast.makeText(contextRef.get(), s, Toast.LENGTH_SHORT);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (TextUtils.equals(s, oldMsg)) {
                    if (twoTime - oneTime > 2000) {
                        toast.show();
                    }
                } else {
                    oldMsg = s;
                    toast.setText(s);
                    toast.show();
                }
            }
            oneTime = twoTime;
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(contextRef.get(), s, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static void showToastCenter(Context context, String s) {
        WeakReference<Context> contextRef = new WeakReference<>(context.getApplicationContext());
        try {
            if (toast == null) {
                toast = Toast.makeText(contextRef.get(), s, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (TextUtils.equals(s, oldMsg)) {
                    if (twoTime - oneTime > 2000) {
                        toast.show();
                    }
                } else {
                    oldMsg = s;
                    toast.setText(s);
                    toast.show();
                }
            }
            oneTime = twoTime;
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(contextRef.get(), s, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }
}
