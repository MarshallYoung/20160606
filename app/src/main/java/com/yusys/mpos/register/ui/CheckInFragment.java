package com.yusys.mpos.register.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登记个人信息界面
 * parent是RegisterActivity,注册流程第5步
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-26 09:43
 */
public class CheckInFragment extends BaseFragment {

    private View fragmentView;
    private RegisterActivity parentActivity;
    @Bind(R.id.edt_name)
    EditText edt_name;
    @Bind(R.id.edt_id)
    EditText edt_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_check_in, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RegisterActivity) getActivity();
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
        parentActivity.toolbar_title.setText("登记个人信息");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(3));
            }
        });
    }

    /**
     * 下一步
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        parentActivity.name = edt_name.getText().toString().trim();
        parentActivity.id = edt_id.getText().toString().trim();
        parentActivity.showFragment(parentActivity.fragments.get(5));
    }
}