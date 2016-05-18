package com.yusys.mpos.base.manager;

import android.util.Log;

import com.yusys.mpos.base.manager.DebugManager;

/**
 * 日志管理
 */
public class LogManager {

    public static void d(String tag, String log) {
        if (DebugManager.Overall.LOG_PRINT)
            Log.d(tag, log);
    }

    public static void e(String tag, String log) {
        if (DebugManager.Overall.LOG_PRINT)
            Log.e(tag, log);
    }

    public static void i(String tag, String log) {
        if (DebugManager.Overall.LOG_PRINT)
            Log.i(tag, log);
    }

    public static void v(String tag, String log) {
        if (DebugManager.Overall.LOG_PRINT)
            Log.v(tag, log);
    }

    public static void w(String tag, String log) {
        if (DebugManager.Overall.LOG_PRINT)
            Log.w(tag, log);
    }
}