package com.yusys.mpos.pay.ui;

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
 * 设备连接
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-25 14:28
 */
public class ConnectionActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    public LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    Button toolbar_right_button;
    FragmentManager fragmentManager;
    public PrepareFragment prepareFragment;
    public DeviceListFragment deviceListFragment;
    public View.OnClickListener finishListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_with_toolbar);
        ButterKnife.bind(this);
        fragmentManager = getFragmentManager();
        initView();
    }

    private void initView() {
        toolbar_title.setText("请连接读卡器");
        toolbar_back.setOnClickListener(finishListener);
        toolbar_right_button.setVisibility(View.INVISIBLE);
        prepareFragment = new PrepareFragment();
        deviceListFragment = new DeviceListFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content, prepareFragment);
        transaction.add(R.id.frame_content, deviceListFragment);
        transaction.commit();
        showFragment(prepareFragment);
    }

    private boolean isInitial = false;// 是否已经初始化,不加这个蹩脚的变量第一次初始化的时候fragment会报空指针

    /**
     * 显示界面
     */
    public void showFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(prepareFragment);
        transaction.hide(deviceListFragment);
        transaction.show(fragment);
        transaction.commit();
        if (isInitial) {
            fragment.onReveal();// 执行此方法为了刷新toolbar
        }
        isInitial = true;
    }
}