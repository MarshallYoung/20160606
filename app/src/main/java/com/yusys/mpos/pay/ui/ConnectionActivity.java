package com.yusys.mpos.pay.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 设备连接
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-25 14:28
 */
public class ConnectionActivity extends ToolbarFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new PrepareFragment());// 0.准备界面
        fragments.add(new DeviceListFragment());// 1.设备选择界面
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("请连接读卡器");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}