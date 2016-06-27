package com.yusys.mpos.register.ui;

import android.graphics.Color;
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
import cn.pedant.SweetAlert.SweetAlertDialog;

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
    @Bind(R.id.tv_bank)
    public TextView tv_bank;// 银行名称
    @Bind(R.id.tv_issue_city)
    public TextView tv_issueCity;// 发卡城市

    SweetAlertDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_choose_account, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RegisterActivity) getActivity();
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
        parentActivity.toolbar_title.setText("选择收款账户");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(6));
            }
        });
    }

    void initList() {
        ListFragment bankFragment = (ListFragment) parentActivity.fragments.get(10);// 银行
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
        ListFragment cityFragment = (ListFragment) parentActivity.fragments.get(11);// 城市
        if (cityFragment.array == null) {
            cityFragment.array = new ArrayList<>();
        }
        cityFragment.array.add("北京");
        cityFragment.array.add("上海");
        cityFragment.array.add("广州");
        cityFragment.array.add("深圳");
        cityFragment.array.add("天津");
        cityFragment.array.add("唐山");
        cityFragment.array.add("郑州");
        cityFragment.array.add("长沙");
        cityFragment.adapter.notifyDataSetChanged();
    }

    /**
     * 选择银行
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_bank)
    void chooseBank(View view) {
        parentActivity.hideKeyboard();
        parentActivity.showFragment(parentActivity.fragments.get(10));
    }

    /**
     * 选择银行
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_issue_city)
    void chooseCity(View view) {
        parentActivity.hideKeyboard();
        parentActivity.showFragment(parentActivity.fragments.get(11));
    }

    /**
     * 下一步
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        parentActivity.hideKeyboard();
        String account = edt_account.getText().toString().trim();
        String card = edt_card.getText().toString().trim();
        String bank = tv_bank.getText().toString().trim();
        String issueCity = tv_issueCity.getText().toString().trim();
        if (account.isEmpty() || card.isEmpty() || bank.isEmpty() || issueCity.isEmpty()) {
            Toast.makeText(getActivity(), "请补全信息", Toast.LENGTH_SHORT).show();
            return;
        }
        parentActivity.account = account;
        parentActivity.card = card;
        parentActivity.bank = bank;
        parentActivity.issueCity = issueCity;

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("上传信息...");
        pDialog.setCancelable(false);
        pDialog.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.cancel();
                        parentActivity.showFragment(parentActivity.fragments.get(8));
                    }
                });
            }
        }, 2000);
    }
}