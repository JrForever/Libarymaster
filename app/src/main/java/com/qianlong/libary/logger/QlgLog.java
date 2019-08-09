package com.qianlong.libary.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 统一日志
 */
public class QlgLog {
    private static boolean IS_DEBUG = false;

    private QlgLog() {

    }

    public static void init(boolean isDebug) {
        IS_DEBUG = isDebug;
    }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
    }

    public static void d(@NonNull String message, @Nullable Object... args) {
        if (IS_DEBUG) {
            Log.d("TAG", message);
        }
    }

    public static void d(@Nullable Object object) {
        if (IS_DEBUG) {
            Log.d("TAG", object.toString());
        }
    }

    public static void d(@NonNull String tag, @Nullable String message) {
        if (IS_DEBUG) {
            Log.d("TAG", tag + "--->" + message);
        }
    }

    public static void e(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        if (IS_DEBUG) {
            Log.e("TAG", tag + "--->" + message);
        }
    }

    public static void e(@NonNull String message, @Nullable Object... args) {
        if (IS_DEBUG) {
            Log.e("TAG", message);
        }
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
    }

    public static void i(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        if (IS_DEBUG) {
            Log.i("TAG", tag + "--->" + message);
        }
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        if (IS_DEBUG) {
            Log.i("TAG", message);
        }
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String message, @Nullable Object... args) {
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@Nullable String json) {
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@Nullable String xml) {
    }
}
