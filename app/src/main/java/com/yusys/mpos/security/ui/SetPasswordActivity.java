package com.yusys.mpos.security.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 设置密码界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-06 14:28
 */
public class SetPasswordActivity extends ToolbarFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new SetPasswordFragment());// 0.设置密码
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("安全管理");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}