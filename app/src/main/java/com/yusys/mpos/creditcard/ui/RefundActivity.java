package com.yusys.mpos.creditcard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ListFragment;
import com.yusys.mpos.base.ui.ToolbarFragmentActivity;
import com.yusys.mpos.merchant.ui.MyGatheringActivity;
import com.yusys.mpos.pay.ui.SwipingFragment;

/**
 * 信用卡还款界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-01 17:01
 */
public class RefundActivity extends ToolbarFragmentActivity {

    public String amount;
    public String card;
    public String bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new RefundFragment());// 0.信用卡还款
        fragments.add(new Refund2Fragment());// 1.信用卡还款确认信息
        SwipingFragment swipingFragment = new SwipingFragment();
        swipingFragment.mode = 1;
        fragments.add(swipingFragment);// 2.刷卡
        ListFragment bankFragment = new ListFragment();
        bankFragment.mode = 3;
        fragments.add(bankFragment);// 3.银行列表
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
                startActivity(new Intent(RefundActivity.this, MyGatheringActivity.class));
            }
        });
    }
}