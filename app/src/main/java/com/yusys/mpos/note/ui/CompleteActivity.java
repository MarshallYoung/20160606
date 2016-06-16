package com.yusys.mpos.note.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 完成界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-30 14:42
 */
public class CompleteActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    public TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    public LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    Button toolbar_right_button;
    @Bind(R.id.tv_note)
    TextView tv_note;
    @Bind(R.id.btn_complete)
    Button btn_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int mode = getIntent().getIntExtra("mode", 0x00);
        if (mode == 0x01) {// 设置密码成功
            toolbar_title.setText("完成");
            tv_note.setText("密码设置成功,请重新登录");
            btn_complete.setText("重新登录");
        } else if (mode == 0x02) {// 还款成功
            toolbar_title.setText("成功");
            tv_note.setText("还款成功");
            btn_complete.setText("确定");
        }
        toolbar_back.setVisibility(View.INVISIBLE);
        toolbar_right_button.setVisibility(View.INVISIBLE);
    }


    /**
     * 完成
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_complete)
    void confirm(View view) {
        finish();
    }
}