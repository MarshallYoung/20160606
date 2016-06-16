package com.yusys.mpos.settings.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改密码界面
 * parent是SettingsFragment
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-27 10:59
 */
public class ChangePasswordFragment extends BaseFragment {

    private View fragmentView;
    SettingsActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_change16, container, false);
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
        parentActivity.toolbar_title.setText("修改密码");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(1));
            }
        });
    }

    /**
     * 修改
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_change)
    void change(View view) {
        getActivity().finish();
    }
}