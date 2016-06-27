package com.yusys.mpos.base.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.AppManager;

import butterknife.ButterKnife;

/**
 * Activity基类
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-03-16
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {

    private InputMethodManager manager;
//    private long touchTime = 0; // 按下后退的时间点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {// 点击空白区域隐藏输入法
            hideKeyboard();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        if (manager.isActive()) {// 输入法打开
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //    @Override
//    public void onBackPressed() {
//        long currentTime = System.currentTimeMillis();
//        long waitTime = 2000;// 两秒内再次点击后退就会退出
//        if ((touchTime + waitTime) < currentTime) {
//            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            touchTime = currentTime;// 刷新触摸时间
//        } else {
//            YXApplication.getInstance().appManager.AppExit(this);
//        }
//    }
}