package com.yusys.mpos.security.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.StackManager;
import com.yusys.mpos.base.ui.BaseActivity;
import com.yusys.mpos.base.widget.SignBoard;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 签名
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-06 14:28
 */
public class SignActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.sign_board)
    SignBoard signBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        StackManager.getInstance().addActivity(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        StackManager.getInstance().finishActivity(this);
    }

    private void initView() {
        toolbar_title.setText("签字");
    }

    /**
     * 清空
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_clear)
    void clear(View view) {
        signBoard.removeAllPaint();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_back)
    void back(View view) {
        finish();
    }
}