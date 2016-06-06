package com.yusys.mpos.security.ui;

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
import com.yusys.mpos.note.ui.CompleteActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置密码界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-06 14:28
 */
public class SetPasswordActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    public LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    Button toolbar_right_button;
    FragmentManager fragmentManager;
    SetPasswordFragment setPasswordFragment;// 设置密码

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

    void initView() {
        toolbar_title.setText("安全管理");
        toolbar_right_button.setVisibility(View.INVISIBLE);
        setPasswordFragment = new SetPasswordFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content, setPasswordFragment);
        transaction.commit();
        showFragment(setPasswordFragment);
    }

    private boolean isInitial = false;// 是否已经初始化,不加这个蹩脚的变量第一次初始化的时候fragment会报空指针

    /**
     * 显示界面
     */
    public void showFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(setPasswordFragment);
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