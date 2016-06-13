package com.yusys.mpos.merchant.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 我的收款界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-06 14:28
 */
public class MyGatheringActivity extends ToolbarFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new MyGatheringFragment());// 0.我的收款
        fragments.add(new QueryReceiveFragment());// 1.到账查询
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("我的收款");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setText("到账查询");
        toolbar_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(fragments.get(1));
            }
        });
    }
}