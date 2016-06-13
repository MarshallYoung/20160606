package com.yusys.mpos.merchant.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 我的付款界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-31 20:39
 */
public class MyPaymentActivity extends ToolbarFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new MyPaymentFragment());// 0.我的付款
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("我的付款");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}