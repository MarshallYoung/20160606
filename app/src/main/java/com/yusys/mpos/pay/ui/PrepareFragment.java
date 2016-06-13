package com.yusys.mpos.pay.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提示信息界面
 * 在展示蓝牙列表之前,parent是ConnectionActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-25 14:38
 */
public class PrepareFragment extends BaseFragment {

    private View fragmentView;
    private ConnectionActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_prepare, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (ConnectionActivity) getActivity();
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onReveal() {
        super.onReveal();
        parentActivity.toolbar_title.setText("请连接读卡器");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.finish();
            }
        });
    }

    /**
     * 连接设备,展示蓝牙列表
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_connect_device)
    void connectDevice(View view) {
        parentActivity.showFragment(parentActivity.fragments.get(1));
    }
}