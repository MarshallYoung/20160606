package com.yusys.mpos.base.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.yusys.mpos.base.YXApplication;

/**
 * Activity基类
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-03-16
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {

    private long touchTime = 0; // 按下后退的时间点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        long waitTime = 2000;// 两秒内再次点击后退就会退出
        if ((touchTime + waitTime) < currentTime) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;// 刷新触摸时间
        } else {
            YXApplication.getInstance().appManager.AppExit(this);
        }
    }
}