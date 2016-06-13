package com.yusys.mpos.creditcard.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 信用卡还款界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-01 17:01
 */
public class RefundActivity extends ToolbarFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new RefundFragment());// 0.信用卡还款
        fragments.add(new Refund2Fragment());// 1.信用卡还款确认信息
        fragments.add(new SwipingCardFragment());// 2.刷卡
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("信用卡还款");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setText("还款记录");
        toolbar_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}