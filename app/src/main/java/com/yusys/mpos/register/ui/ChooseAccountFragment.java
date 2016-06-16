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
 * 选择收款账户界面
 * parent是RegisterActivity,注册流程第8步
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-26 09:43
 */
public class ChooseAccountFragment extends BaseFragment {

    private View fragmentView;
    private RegisterActivity parentActivity;

    @Bind(R.id.edt_account)
    EditText edt_account;// 账户户名
    @Bind(R.id.edt_card)
    EditText edt_card;// 卡号
    @Bind(R.id.edt_bank)
    EditText edt_bank;// 银行名称
    @Bind(R.id.edt_issue_city)
    EditText edt_issueCity;// 发卡城市

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_choose_account, container, false);
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
        parentActivity.toolbar_title.setText("选择收款账户");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(6));
            }
        });
    }

    /**
     * 下一步
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        parentActivity.account = edt_account.getText().toString().trim();
        parentActivity.card = edt_card.getText().toString().trim();
        parentActivity.bank = edt_bank.getText().toString().trim();
        parentActivity.issueCity = edt_issueCity.getText().toString().trim();
        parentActivity.showFragment(parentActivity.fragments.get(8));
    }
}