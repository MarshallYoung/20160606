package com.yusys.mpos.creditcard.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.base.ui.ListFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 信用卡还款界面
 * parent是RefundActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class RefundFragment extends BaseFragment {

    private View fragmentView;
    private RefundActivity parentActivity;

    @Bind(R.id.edt_credit_card)
    EditText edt_creditCard;
    @Bind(R.id.tv_bank)
    public TextView tv_bank;
    @Bind(R.id.edt_amount)
    EditText edt_amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_refund, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RefundActivity) getActivity();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    initList();
                }
            }, 500);
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
                parentActivity.finish();
            }
        });
    }

    void initList() {
        ListFragment bankFragment = (ListFragment) parentActivity.fragments.get(3);// 城市
        if (bankFragment.array == null) {
            bankFragment.array = new ArrayList<>();
        }
        bankFragment.array.add("工商银行");
        bankFragment.array.add("农业银行");
        bankFragment.array.add("中国银行");
        bankFragment.array.add("建设银行");
        bankFragment.array.add("招商银行");
        bankFragment.array.add("民生银行");
        bankFragment.array.add("兴业银行");
        bankFragment.adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.ll_bank)
    void bank(View view) {
        parentActivity.showFragment(parentActivity.fragments.get(3));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        parentActivity.hideKeyboard();

        String card = edt_creditCard.getText().toString().trim();
        String bank = tv_bank.getText().toString().trim();
        String amount = edt_amount.getText().toString().trim();
        if (card.isEmpty()) {
            Toast.makeText(getActivity(), "请输入卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bank.isEmpty()) {
            Toast.makeText(getActivity(), "请选择银行", Toast.LENGTH_SHORT).show();
            return;
        }
        if (amount.isEmpty()) {
            Toast.makeText(getActivity(), "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        parentActivity.amount = amount;
        parentActivity.card = card;
        parentActivity.bank = bank;
        parentActivity.showFragment(parentActivity.fragments.get(1));
    }
}