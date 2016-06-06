package com.yusys.mpos.merchant.ui;

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

/**
 * 我的收款界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-06 14:28
 */
public class MyGatheringActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    public LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    public Button toolbar_right_button;
    FragmentManager fragmentManager;
    MyGatheringFragment myGatheringFragment;// 我的收款
    QueryReceiveFragment queryReceiveFragment;// 到账查询

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_with_toolbar);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        initView();
    }

    void initView() {
        toolbar_title.setText("我的收款");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setVisibility(View.VISIBLE);
        toolbar_right_button.setText("到账查询");
        toolbar_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(queryReceiveFragment);
            }
        });
        myGatheringFragment = new MyGatheringFragment();
        queryReceiveFragment = new QueryReceiveFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content, myGatheringFragment);
        transaction.add(R.id.frame_content, queryReceiveFragment);
        transaction.commit();
        showFragment(myGatheringFragment);
    }

    private boolean isInitial = false;// 是否已经初始化,不加这个蹩脚的变量第一次初始化的时候fragment会报空指针

    /**
     * 显示界面
     */
    public void showFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(myGatheringFragment);
        transaction.hide(queryReceiveFragment);
        transaction.show(fragment);
        transaction.commit();
        if (isInitial) {
            fragment.onReveal();// 执行此方法为了刷新toolbar
        }
        isInitial = true;
    }
}