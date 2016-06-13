package com.yusys.mpos.settings.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.AppManager;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.login.ui.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 设置界面
 * parent是SettingsFragment
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-27 10:59
 */
public class SettingsFragment extends BaseFragment {

    private View fragmentView;
    private SettingsActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (SettingsActivity) getActivity();
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onReveal() {
        super.onReveal();
        parentActivity.toolbar_title.setText("设置");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.finish();
            }
        });
    }

    /**
     * 账户安全
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_security)
    void security(View view) {
        parentActivity.showFragment(parentActivity.fragments.get(1));
    }

    /**
     * 协议
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_agreements)
    void agreements(View view) {
        parentActivity.showFragment(parentActivity.fragments.get(2));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.ll_exit)
    void exit(View view) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText("您确定要退出系统吗?")
                .setCancelText("取消")
                .setConfirmText("确定!")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        AppManager.getInstance().finishAllActivity();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                }).show();
    }
}