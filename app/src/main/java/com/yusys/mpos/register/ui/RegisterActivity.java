package com.yusys.mpos.register.ui;

import android.os.Bundle;
import android.view.View;

import com.yusys.mpos.base.ui.ListFragment;
import com.yusys.mpos.base.ui.ToolbarFragmentActivity;
import com.yusys.mpos.security.ui.SetPasswordFragment;

/**
 * 注册界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-05 14:52
 */
public class RegisterActivity extends ToolbarFragmentActivity {

    public String mobilePhone;// 0.手机号
    public int type;// 2.类型:    0-个人    1-商户
    public String name;// 4.姓名
    public String id;// 4.身份证号码
    public String domain;// 5.从事产业
    public String city;// 6.城市
    public String address;// 6.地址
    public String bill;// 6.票据名称
    public String account;// 7.账户户名
    public String card;// 7.账户卡号
    public String bank;// 7.银行名称
    public String issueCity;// 7.发卡城市

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initFragments() {
        fragments.add(new MobilePhoneFragment());// 0.填写手机号
        fragments.add(new VerifyRateFragment());// 1.核对费率
        fragments.add(new ChooseTypeFragment());// 2.选择类型
        fragments.add(new CertificatesFragment());// 3.证件照片
        fragments.add(new CheckInFragment());// 4.登记个人信息
        fragments.add(new ChooseDomainFragment());// 5.选择从事产业
        fragments.add(new LocationFragment());// 6.登记经营信息
        fragments.add(new ChooseAccountFragment());// 7.选择收款账户
        fragments.add(new SuccessFragment());// 8.提交成功
        ListFragment locationFragment = new ListFragment();
        locationFragment.mode = 0;
        fragments.add(locationFragment);// 9.地址列表
        ListFragment bankFragment = new ListFragment();
        bankFragment.mode = 1;
        fragments.add(bankFragment);// 10.银行列表
        ListFragment cityFragment = new ListFragment();
        cityFragment.mode = 2;
        fragments.add(cityFragment);// 11.城市列表
        fragments.add(new SetPasswordFragment());// 12.设置密码
        super.initFragments();
    }

    @Override
    public void initView() {
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