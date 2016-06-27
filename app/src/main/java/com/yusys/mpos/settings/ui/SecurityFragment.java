package com.yusys.mpos.settings.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户安全界面
 * parent是SettingsFragment
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-27 10:59
 */
public class SecurityFragment extends BaseFragment {

    private View fragmentView;
    SettingsActivity parentActivity;
    @Bind(R.id.cb_gesture)
    CheckBox cb_gesture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_security, container, false);
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
        parentActivity.toolbar_title.setText("");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(0));
            }
        });
    }

    /**
     * 修改密码
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_change_password)
    void changePassword(View view) {
        parentActivity.showFragment(parentActivity.fragments.get(3));
    }

    /**
     * 手势密码
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_gesture)
    void gesture(View view) {
        cb_gesture.setChecked(!cb_gesture.isChecked());
    }
}