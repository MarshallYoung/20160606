package com.yusys.mpos.security.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yusys.mpos.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 获取手机验证码
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-10 11:29
 */
public class VerificationCodeFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.edt_mobile_phone)
    public EditText edt_mobliephone;// 手机号
    @Bind(R.id.edt_verification_code)
    public EditText edt_verificationCode;// 验证码
    @Bind(R.id.btn_get_verification)
    public Button btn_getVerification;// 获取验证码

    public int operation;
    public static final int OPERATION_REGISTER = 1;// 注册
    public static final int OPERATION_FIND_PASSWORD = 2;// 找回密码
    public static final int OPERATION_RESET_PASSWORD = 3;// 重置密码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_verification_code,
                    container, false);
            ButterKnife.bind(this, fragmentView);
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

    // 获取验证码
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_get_verification)
    void getVerification() {
        btn_getVerification.setEnabled(false);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 10;

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_getVerification.setText(i + "秒");
                    }
                });
                i--;
                if (i <= 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_getVerification.setEnabled(true);
                            btn_getVerification.setText("获取验证码");
                            timer.cancel();
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

}