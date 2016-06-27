package com.yusys.mpos.settings.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

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

    @Bind(R.id.edt_old16)
    EditText edt_old16;
    @Bind(R.id.edt_16)
    EditText edt_16;
    @Bind(R.id.edt_17)
    EditText edt_17;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_change16, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (SettingsActivity) getActivity();
            edt_old16.setInputType(0x81);
            edt_16.setInputType(0x81);
            edt_17.setInputType(0x81);
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
        parentActivity.hideKeyboard();

        String oldpwd = edt_old16.getText().toString().trim();
        String pwd = edt_16.getText().toString().trim();
        String repwd = edt_17.getText().toString().trim();
        if (oldpwd.isEmpty() || pwd.isEmpty() || repwd.isEmpty()) {
            Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!oldpwd.equals("123456")) {
            Toast.makeText(getActivity(), "原密码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.length() < 6) {
            Toast.makeText(getActivity(), "密码最少为6位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(repwd)) {
            Toast.makeText(getActivity(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("提示")
                .setContentText("密码修改成功")
                .setConfirmText("确定!")
                .showCancelButton(false)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        getActivity().finish();
                    }
                }).show();
    }
}