package com.yusys.mpos.security.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yusys.mpos.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设置密码
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class SetPasswordFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.edt_password)
    public EditText edt_password;// 密码
    @Bind(R.id.edt_confirm_password)
    public EditText edt_confirm_password;// 确认密码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_set_password,
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
}