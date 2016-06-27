package com.yusys.mpos.security.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.base.ui.ToolbarFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置密码界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class SetPasswordFragment extends BaseFragment {

    private View fragmentView;
    private ToolbarFragmentActivity parentActivity;

    @Bind(R.id.edt_16)
    public EditText edt_16;// 密码
    @Bind(R.id.edt_17)
    public EditText edt_17;// 确认密码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_set16, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (ToolbarFragmentActivity) getActivity();
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
    public void onReveal() {
        super.onReveal();
        parentActivity.toolbar_title.setText("设置密码");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(0));
            }
        });
    }

    /**
     * 确定
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_confirm)
    void confirm(View view) {
        parentActivity.hideKeyboard();

        String pwd = edt_16.getText().toString().trim();
        String repwd = edt_17.getText().toString().trim();
        if (pwd.isEmpty() || repwd.isEmpty()) {
            Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
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
        parentActivity.showFragment(parentActivity.fragments.get(2));
    }
}