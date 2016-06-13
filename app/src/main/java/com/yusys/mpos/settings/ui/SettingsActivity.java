package com.yusys.mpos.settings.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 设置界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-27 10:53
 */
public class SettingsActivity extends ToolbarFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new SettingsFragment());// 0.设置
        fragments.add(new SecurityFragment());// 1.安全
        fragments.add(new AgreementsFragment());// 2.协议
        fragments.add(new ChangePasswordFragment());// 3.修改密码
        super.initFragments();
    }

    @Override
    public void initView() {
        toolbar_title.setText("设置");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}