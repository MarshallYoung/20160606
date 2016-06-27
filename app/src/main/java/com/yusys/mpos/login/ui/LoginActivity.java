package com.yusys.mpos.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.APKManager;
import com.yusys.mpos.base.manager.DeviceManager;
import com.yusys.mpos.base.manager.LogManager;
import com.yusys.mpos.base.manager.Validator;
import com.yusys.mpos.base.ui.BaseActivity;
import com.yusys.mpos.main.ui.MainActivity;
import com.yusys.mpos.register.ui.RegisterActivity;
import com.yusys.mpos.security.ui.SetPasswordActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 登录界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-01
 */
public class LoginActivity extends BaseActivity {

    interface Preferences {
        String FILENAME = "loginpref";
        String REMEMBER_USERNAME = "rememberusername";
        String USERNAME = "username";
    }

    @Bind(R.id.edt_mobile_phone)
    EditText edt_mobile_phone;
    @Bind(R.id.edt_16)
    EditText edt_16;
    @Bind(R.id.ckb_remember_username)
    CheckBox ckb_remember;

    //    private KeyboardManager keyboardManager;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);// 注解绑定控件的框架
        preferences = getSharedPreferences(Preferences.FILENAME, MODE_PRIVATE);
        initView();
    }

    private void initView() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("初始化程序...");
        pDialog.setCancelable(false);
        pDialog.show();
        if (DeviceManager.haveRoot()) {// 确认是否Root
            pDialog.cancel();
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("注意")
                    .setContentText("设备已Root,为了您的资金安全,请使用未Root设备安装app.")
                    .setConfirmText("退出app")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            LoginActivity.this.finish();
                        }
                    }).show();
        } else {// 未Root
            edt_16.setInputType(0x81);
            pDialog.cancel();
            // 是否记住用户名
            Boolean remember = preferences.getBoolean(Preferences.REMEMBER_USERNAME, false);
            if (remember) {// 回显用户名
                String username = preferences.getString(Preferences.USERNAME, "");
                edt_mobile_phone.setText(username);
                ckb_remember.setChecked(true);
            }
        }
        String s = APKManager.getSign(this);
        APKManager.getSingInfo(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.ckb_remember_username)
    void onChecked(View view) {
        Boolean isChecked = ((CheckBox) view).isChecked();
        String username = "";
        if (isChecked) {// 保存用户名
            username = edt_mobile_phone.getText().toString().trim();
        }
        preferences.edit().putBoolean(Preferences.REMEMBER_USERNAME, isChecked).apply();
        preferences.edit().putString(Preferences.USERNAME, username).apply();
    }

    // 注册
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_register)
    void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // 找回密码
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_forget16)
    void findPassword(View view) {
        Intent intent = new Intent(this, SetPasswordActivity.class);
        startActivity(intent);
    }

    // 登录
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login)
    void login(View view) {
        hideKeyboard();
        String phoneNum = edt_mobile_phone.getText().toString().trim();
        String pwd = edt_16.getText().toString().trim();
        if (APKManager.DEBUG) {// 调试

        } else {// 正常模式
            if (phoneNum.isEmpty()) {// 手机为空
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validator.isMobile(phoneNum)) {// 手机号不合法
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pwd.isEmpty()) {// 密码为空
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (!phoneNum.equals("13888888888")) {
            Toast.makeText(this, "号码错误,无此号码信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals("123456")) {
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        String IMEI = DeviceManager.getIMEI(this);
        LogManager.e("==登录界面==", "设备的IMEI是" + IMEI);// 设备唯一识别码
        Boolean isChecked = ckb_remember.isChecked();
        if (isChecked) {// 保存用户名
            preferences.edit().putBoolean(Preferences.REMEMBER_USERNAME, true).apply();
            preferences.edit().putString(Preferences.USERNAME, phoneNum).apply();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}