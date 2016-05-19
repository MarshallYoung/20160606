package com.yusys.mpos.login.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.security.ui.SetPasswordFragment;
import com.yusys.mpos.security.ui.VerificationCodeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-05 14:52
 */
public class RegisterActivity extends Activity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    FragmentManager fragmentManager;
    VerificationCodeFragment verificationCodeFragment;
    SetPasswordFragment setPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        verificationCodeFragment = new VerificationCodeFragment();
        setPasswordFragment = new SetPasswordFragment();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    void initView() {
        toolbar_title.setText("注册");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_from_bottom, R.animator.slide_out_to_top);
        transaction.replace(R.id.frame_content, verificationCodeFragment);
        transaction.commit();
    }

    // 后退
    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_back)
    void goBack(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    // 下一步
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_from_bottom, R.animator.slide_out_to_top);
        transaction.hide(verificationCodeFragment).add(R.id.frame_content, setPasswordFragment);
        transaction.commit();
    }
}