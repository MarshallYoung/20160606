package com.yusys.mpos.register.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

/**
 * 注册界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-05 14:52
 */
public class RegisterActivity extends ToolbarFragmentActivity {


    String mobilePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments();
        initView();
    }

    @Override
    public void initFragments() {
        fragments.add(new MobilePhoneFragment());// 1.填写手机号
        fragments.add(new VerifyRateFragment());// 2.核对费率
        fragments.add(new ChooseTypeFragment());// 3.选择类型
        fragments.add(new CertificatesFragment());// 4.证件照片
        fragments.add(new CheckInFragment());// 5.登记个人信息
        fragments.add(new ChooseDomainFragment());// 6.选择从事产业
        fragments.add(new LocationFragment());// 7.登记经营信息
        fragments.add(new ChooseAccountFragment());// 8.选择收款账户
        fragments.add(new SuccessFragment());// 9.提交成功
        super.initFragments();
    }

    void initView() {
        toolbar_title.setText("输入手机号码");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}