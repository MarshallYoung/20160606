package com.yusys.mpos.register.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择类型的界面
 * parent是RegisterActivity,注册流程第3步
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-26 09:43
 */
public class ChooseTypeFragment extends BaseFragment {

    private View fragmentView;
    private RegisterActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_choose_type, container, false);
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
        parentActivity.toolbar_title.setText("选择类型");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(1));
            }
        });
    }

    /**
     * 个人
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_type_personal)
    void typePersonal(View view) {
        Toast.makeText(getActivity(), "个人", Toast.LENGTH_SHORT).show();
        parentActivity.type = 0;
    }

    /**
     * 商户
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_type_merchant)
    void typeMerchant(View view) {
        parentActivity.type = 1;
        parentActivity.showFragment(parentActivity.fragments.get(3));
    }
}
