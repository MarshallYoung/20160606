package com.yusys.mpos.register.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.APKManager;
import com.yusys.mpos.base.manager.Validator;
import com.yusys.mpos.base.ui.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 输入手机号的界面
 * parent是RegisterActivity,注册流程第1步
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-26 09:43
 */
public class MobilePhoneFragment extends BaseFragment {

    private View fragmentView;
    private RegisterActivity parentActivity;
    @Bind(R.id.edt_mobile_phone)
    EditText edt_mobilePhone;
    @Bind(R.id.edt_identifying)
    public EditText edt_identifying;// 验证码
    @Bind(R.id.btn_get_identifying)
    public Button btn_getIdentifying;// 获取验证码

    private Timer timer;
    private TimerTask task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_mobile_phone, container, false);
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
    public void onPause() {
        super.onPause();
        if (task != null) {
            task.cancel();
            btn_getIdentifying.setEnabled(true);
            btn_getIdentifying.setText("获取验证码");
        }
    }

    @Override
    public void onReveal() {
        super.onReveal();
        parentActivity.toolbar_title.setText("输入手机号");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.finish();
            }
        });
    }

    // 获取验证码
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_get_identifying)
    void getVerification() {
        btn_getIdentifying.setEnabled(false);
        if (timer == null) {
            timer = new Timer();
        }
        if (task == null) {
            task = new TimerTask() {
                public int i = 10;

                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_getIdentifying.setText(i + "秒");
                        }
                    });
                    i--;
                    if (i <= 0) {
                        reset();
                    }
                }

                public void reset() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_getIdentifying.setEnabled(true);
                            btn_getIdentifying.setText("获取验证码");
                            i = 10;
                            task.cancel();
                        }
                    });
                }
            };
        }
        timer.schedule(task, 1000, 1000);
    }

    /**
     * 下一步
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        parentActivity.hideKeyboard();
        String phoneNum = edt_mobilePhone.getText().toString().trim();
        String code = edt_identifying.getText().toString().trim();
        if (APKManager.DEBUG) {// 调试

        } else {// 正常模式
            if (phoneNum.isEmpty()) {// 手机为空
                Toast.makeText(getActivity(), "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validator.isMobile(phoneNum)) {// 手机号不合法
                Toast.makeText(getActivity(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (code.isEmpty()) {// 验证码为空
                Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!phoneNum.equals("13888888888")) {
            Toast.makeText(getActivity(), "号码错误,无此号码信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!code.equals("123456")) {
            Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        parentActivity.mobilePhone = phoneNum;
        parentActivity.showFragment(parentActivity.fragments.get(12));
    }
}