package com.yusys.mpos.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseActivity;
import com.yusys.mpos.base.widget.keyboard.KeyboardManager;
import com.yusys.mpos.main.ui.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-01
 */
public class LoginActivity extends BaseActivity {

    private interface Preferences {
        String FILENAME = "loginpref";
        String REMEMBER_USERNAME = "rememberusername";
        String USERNAME = "username";
    }

    private interface Notes {
        String EMPTY_USERNAME = "请输入用户名";
        String EMPTY_PASSWORD = "请输入密码";
    }

    @Bind(R.id.edt_username)
    EditText edt_username;
    @Bind(R.id.edt_password)
    EditText edt_password;
    @Bind(R.id.ckb_remember_username)
    CheckBox ckb_remember;

    private KeyboardManager keyboardManager;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);// 注解绑定控件的框架
        preferences = getSharedPreferences(Preferences.FILENAME, MODE_PRIVATE);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        keyboardManager.hideKeyboard();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (keyboardManager != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                keyboardManager.hideKeyboard();
            }
        }
        return super.onTouchEvent(event);
    }

    private void initView() {
        // 是否记住用户名
        Boolean remember = preferences.getBoolean(Preferences.REMEMBER_USERNAME, false);
        if (remember) {// 回显用户名
            String username = preferences.getString(Preferences.USERNAME, "");
            edt_username.setText(username);
            ckb_remember.setChecked(true);
        }
        keyboardManager = KeyboardManager.getInstance();
        edt_username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {// 限定手指抬起的时候调用,否则会调用2次
                    keyboardManager.setTarget(edt_username);
                    keyboardManager.showKeyboard();
                }
                return false;
            }
        });
        edt_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {// 限定手指抬起的时候调用,否则会调用2次
                    keyboardManager.setTarget(edt_password);
                    keyboardManager.showKeyboard();
                }
                return false;
            }
        });
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.ckb_remember_username)
    void onChecked(View view) {
        Boolean isChecked = ((CheckBox) view).isChecked();
        String username = "";
        if (isChecked) {// 保存用户名
            username = edt_username.getText().toString().trim();
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
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    // 找回密码
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_forget_password)
    void findPassword(View view) {
        // TODO 找回密码
    }

    // 登录
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_login)
    void login(View view) {
        String username = edt_username.getText().toString().trim();
        if (username.isEmpty()) {// 用户名为空
            Toast.makeText(this, Notes.EMPTY_USERNAME, Toast.LENGTH_SHORT).show();
            return;
        }
        String password = edt_password.getText().toString().trim();
        if (password.isEmpty()) {// 密码为空
            Toast.makeText(this, Notes.EMPTY_PASSWORD, Toast.LENGTH_SHORT).show();
            return;
        }
        Boolean isChecked = ckb_remember.isChecked();
        if (isChecked) {// 保存用户名
            preferences.edit().putBoolean(Preferences.REMEMBER_USERNAME, true).apply();
            preferences.edit().putString(Preferences.USERNAME, username).apply();
        }
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        finish();
    }
}