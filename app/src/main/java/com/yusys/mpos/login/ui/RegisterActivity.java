package com.yusys.mpos.login.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yusys.mpos.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-05 14:52
 */
public class RegisterActivity extends Activity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    View btn_back;
    @Bind(R.id.edt_verification)
    EditText edt_verification;// 验证码
    @Bind(R.id.btn_get_verification)
    Button btn_getVerification;// 获取验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    void initView() {
        toolbar_title.setText("注册");
    }

    // 后退
    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_back)
    void goBack(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_getVerification.setText(i + "秒");
                    }
                });
                i--;
                if (i <= 0) {
                    runOnUiThread(new Runnable() {
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