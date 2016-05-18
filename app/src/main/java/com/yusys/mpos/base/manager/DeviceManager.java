package com.yusys.mpos.base.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.yusys.mpos.base.YXApplication;

import java.io.File;

/**
 * 设备信息
 */
public class DeviceManager {

    /**
     * 得到设备屏幕参数
     *
     * @return 设备参数
     */
    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) YXApplication.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics;
    }

    /**
     * 得到设备的屏幕高度
     *
     * @return 屏幕高度
     */
    public static float getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static float getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 判断网络状态
     *
     * @return 是否联网
     */
    public static boolean isInternetConnected() {
        ConnectivityManager manager = (ConnectivityManager) YXApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    /**
     * 判断Wifi状态
     *
     * @return 是否连接Wifi
     */
    public static boolean isWifiConnected() {
        if (!isInternetConnected()) {
            return false;
        } else {
            ConnectivityManager manager = (ConnectivityManager) YXApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return info.getType() == ConnectivityManager.TYPE_WIFI;
        }
    }

    /**
     * 隐藏虚拟键盘
     *
     * @param view 背景界面
     */
    public static void hideSoftKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) YXApplication.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示虚拟键盘
     *
     * @param view 背景界面
     */
    public static void showSoftKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) YXApplication.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 得到程序版本号
     *
     * @return 程序版本号
     */
    public static int getVersionCode() {
        String pkgName = YXApplication.getInstance().getPackageName();
        return getVersionCode(pkgName);
    }

    /**
     * 得到程序版本号
     *
     * @param packageName 程序包名
     * @return 程序版本号
     */
    public static int getVersionCode(String packageName) {
        int versionCode = 0;
        try {
            versionCode = YXApplication.getInstance().getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 安装Apk
     *
     * @param context 上下文
     * @param file    Apk文件
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}