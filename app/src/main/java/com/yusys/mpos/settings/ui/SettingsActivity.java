package com.yusys.mpos.settings.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.AppManager;
import com.yusys.mpos.base.ui.BaseActivity;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-27 10:53
 */
public class SettingsActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    public LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    Button toolbar_right_button;
    FragmentManager fragmentManager;
    public SettingsFragment settingsFragment;
    public SecurityFragment securityFragment;
    public AgreementsFragment agreementsFragment;
    public ChangePasswordFragment changePasswordFragment;
    public View.OnClickListener finishListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    public View.OnClickListener clickToShowSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFragment(settingsFragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_with_toolbar);
        ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        fragmentManager = getFragmentManager();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.getInstance().finishActivity(this);
    }

    private void initView() {
        toolbar_right_button.setVisibility(View.INVISIBLE);
        settingsFragment = new SettingsFragment();
        securityFragment = new SecurityFragment();
        agreementsFragment = new AgreementsFragment();
        changePasswordFragment = new ChangePasswordFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content, settingsFragment);
        transaction.add(R.id.frame_content, securityFragment);
        transaction.add(R.id.frame_content, agreementsFragment);
        transaction.add(R.id.frame_content, changePasswordFragment);
        transaction.commit();
        showFragment(settingsFragment);
    }

    private boolean isInitial = false;// 是否已经初始化,不加这个蹩脚的变量第一次初始化的时候fragment会报空指针

    /**
     * 显示界面
     */
    public void showFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(settingsFragment);
        transaction.hide(securityFragment);
        transaction.hide(agreementsFragment);
        transaction.hide(changePasswordFragment);
        transaction.show(fragment);
        transaction.commit();
        if (isInitial) {
            fragment.onReveal();// 执行此方法为了刷新toolbar
        }
        isInitial = true;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_back)
    void onClick(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}