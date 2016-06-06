package com.yusys.mpos.main.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.merchant.ui.MyAccountActivity;
import com.yusys.mpos.merchant.ui.MyGatheringActivity;
import com.yusys.mpos.merchant.ui.MyPaymentActivity;
import com.yusys.mpos.settings.ui.SettingsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 个人信息界面
 * parent是MainActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-06 17:26
 */
public class MeFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_personal_info, container, false);
            ButterKnife.bind(this, fragmentView);
            toolbar_title.setText("我");
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_settings)
    void settings(View view) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * 我的账户
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_my_account)
    void myAccount(View view) {
        getActivity().startActivity(new Intent(getActivity(), MyAccountActivity.class));
    }

    /**
     * 我的收款
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_my_gathering)
    void myGathering(View view) {
        getActivity().startActivity(new Intent(getActivity(), MyGatheringActivity.class));
    }

    /**
     * 我的付款
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_my_payment)
    void myPayment(View view) {
        getActivity().startActivity(new Intent(getActivity(), MyPaymentActivity.class));
    }

    /**
     * 联系我们
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_contact_us)
    void securityManage(View view) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText("客服电话:400-XXX-XXXX")
                .setCancelText("取消")
                .setConfirmText("直接拨打")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "13888888888"));
                        getActivity().startActivity(intent);
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