package com.yusys.mpos.creditcard.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseActivity;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 信用卡还款界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-01 17:01
 */
public class RefundActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    public LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    public Button toolbar_right_button;
    FragmentManager fragmentManager;
    RefundFragment refundFragment;// 信用卡还款
    Refund2Fragment refund2Fragment;// 信用卡还款确认信息
    SwipingCardFragment swipingCardFragment;// 刷卡

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_with_toolbar);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        initView();
    }

    void initView() {
        toolbar_title.setText("信用卡还款");
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar_right_button.setText("还款记录");
        toolbar_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        refundFragment = new RefundFragment();
        refund2Fragment = new Refund2Fragment();
        swipingCardFragment = new SwipingCardFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content, refundFragment);
        transaction.add(R.id.frame_content, refund2Fragment);
        transaction.add(R.id.frame_content, swipingCardFragment);
        transaction.commit();
        showFragment(refundFragment);
    }

    private boolean isInitial = false;// 是否已经初始化,不加这个蹩脚的变量第一次初始化的时候fragment会报空指针

    /**
     * 显示界面
     */
    public void showFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(refundFragment);
        transaction.hide(refund2Fragment);
        transaction.hide(swipingCardFragment);
        transaction.show(fragment);
        transaction.commit();
        if (isInitial) {
            fragment.onReveal();// 执行此方法为了刷新toolbar
        }
        isInitial = true;
    }
}