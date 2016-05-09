package com.yusys.mpos.base.util;

import android.util.Log;

/**
 * 日志工具
 */
public class LogUtil {
    public static final String LOG_TAG = "==mPos==";
    public static boolean DEBUG = true;// 是否开启日志

    public static void d(String log) {
        if (DEBUG)
            Log.d(LOG_TAG, log);
    }

    public static void e(String log) {
        if (DEBUG)
            Log.e(LOG_TAG, "" + log);
    }

    public static void i(String log) {
        if (DEBUG)
            Log.i(LOG_TAG, log);
    }

    public static void i(String tag, String log) {
        if (DEBUG)
            Log.i(tag, log);
    }

    public static void v(String log) {
        if (DEBUG)
            Log.v(LOG_TAG, log);
    }

    public static void w(String log) {
        if (DEBUG)
            Log.w(LOG_TAG, log);
    }

}