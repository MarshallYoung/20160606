package com.yusys.mpos.base;

import android.app.Application;

import com.yusys.mpos.base.manager.AppManager;

/**
 * Application
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-03-16
 */
public class YXApplication extends Application {

    private static YXApplication instance;
    public AppManager appManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appManager = AppManager.getInstance();
    }

    public static YXApplication getInstance() {
        return instance;
    }

}