package com.yusys.mpos.creditcard.ui;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 信用卡还款界面
 * parent是RefundActivity
 * 第二步,确认还款信息
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class Refund2Fragment extends BaseFragment {

    private View fragmentView;
    private RefundActivity parentActivity;

    @Bind(R.id.tv_bank)
    TextView tv_bank;
    @Bind(R.id.tv_card)
    TextView tv_card;
    @Bind(R.id.tv_amount)
    TextView tv_amount;
    @Bind(R.id.tv_arrival_time)
    TextView tv_arrivalTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_refund2, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RefundActivity) getActivity();
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
        parentActivity.toolbar_title.setText("信用卡还款");
        parentActivity.toolbar_right_button.setVisibility(View.VISIBLE);
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(0));
            }
        });
        tv_bank.setText(parentActivity.bank);
        tv_card.setText(parentActivity.card);
        tv_amount.setText(parentActivity.amount);
    }

    /**
     * 到账时间
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_arrival_time)
    void arrivalTime(View view) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("请选择到账时间")
                .setCancelText("T+1")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        tv_arrivalTime.setText("T+1");
                        Resources resource = getActivity().getBaseContext().getResources();
                        ColorStateList csl = resource.getColorStateList(R.color.gray_text);
                        if (csl != null) {
                            tv_arrivalTime.setTextColor(csl);
                        }
                    }
                })
                .setConfirmText("立即到账")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        tv_arrivalTime.setText("立即到账");
                        Resources resource = getActivity().getBaseContext().getResources();
                        ColorStateList csl = resource.getColorStateList(R.color.orange);
                        if (csl != null) {
                            tv_arrivalTime.setTextColor(csl);
                        }
                    }
                }).show();
    }

    /**
     * 确认刷卡
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_confirm)
    void confirm(View view) {
        parentActivity.showFragment(parentActivity.fragments.get(2));
    }
}