package com.yusys.mpos.merchant.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 我的账户界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-06 14:28
 */
public class MyAccountActivity extends ToolbarFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new MyAccountFragment());// 我的账户
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("我的账户");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}