package com.yusys.mpos.me.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yusys.mpos.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 安全管理(密码管理),修改各种密码的界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-06 14:28
 */
public class PasswordManageActivity extends Activity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    View btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(null);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    void initView() {
        toolbar_title.setText("安全管理");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_back)
    void onClick(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}