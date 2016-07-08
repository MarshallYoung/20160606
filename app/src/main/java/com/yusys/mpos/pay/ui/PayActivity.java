package com.yusys.mpos.pay.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.manager.LogUtil;
import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 支付界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-21 11:16
 */
public class PayActivity extends ToolbarFragmentActivity {

    public double amount;

    private SweetAlertDialog tradeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amount = getIntent().getDoubleExtra("amount", 0);
        LogUtil.e("== Pay ==", amount + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        tradeDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        tradeDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        tradeDialog.setCancelable(false);
        tradeDialog.setTitleText("初始化交易...").show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showFragment(fragments.get(0));
                        tradeDialog.cancel();
                    }
                });
            }
        }, 500);
    }

    @Override
    public void initFragments() {
        SwipingFragment swipingFragment = new SwipingFragment();
        swipingFragment.mode = 0;
        fragments.add(swipingFragment);// 0.刷卡
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("请刷卡或插入IC卡");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}