package com.yusys.mpos.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.StackManager;

import butterknife.ButterKnife;

/**
 * Activity基类
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-03-16
 */
public class BaseActivity extends Activity {

    private InputMethodManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        StackManager.getInstance().addActivity(this);
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
        StackManager.getInstance().finishActivity(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {// 点击空白区域隐藏输入法
            hideKeyboard();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 带动画的转场
     */
    public void startActivityWithAnim(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    /**
     * 带动画的关闭
     */
    public void finishWithAnim() {
        finish();
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
}