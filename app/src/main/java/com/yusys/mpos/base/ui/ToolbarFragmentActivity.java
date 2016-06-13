package com.yusys.mpos.base.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yusys.mpos.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 带有toolbar导航栏的,用以承载多个fragment的Activity基类
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-02 16:36
 */
public class ToolbarFragmentActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    public LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    public Button toolbar_right_button;

    public ArrayList<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_with_toolbar);
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        initFragments();
        initView();
    }

    /**
     * 初始化fragments,super.initFragments这一步一定要写在fragments添加完之后
     */
    public void initFragments() {
        if (fragments.size() < 1) {
            return;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            transaction.add(R.id.frame_content, fragments.get(i));
        }
        transaction.commit();
        showFragment(fragments.get(0));
    }

    /**
     * 初始化界面
     */
    public void initView() {

    }


    private boolean isInitial = false;// 是否已经初始化,不加这个蹩脚的变量第一次初始化的时候fragment会报空指针

    /**
     * 显示界面
     */
    public void showFragment(BaseFragment fragment) {
        if (fragments.size() < 1) {
            return;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            transaction.hide(fragments.get(i));
        }
        transaction.show(fragment);
        transaction.commit();
        if (isInitial) {
            fragment.onReveal();// 执行此方法为了刷新toolbar
        }
        isInitial = true;
    }
}