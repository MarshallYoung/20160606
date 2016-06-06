package com.yusys.mpos.security.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.note.NoteAPI;
import com.yusys.mpos.note.ui.CompleteActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置密码界面
 * parent是SetPasswordActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class SetPasswordFragment extends BaseFragment {

    private View fragmentView;
    private SetPasswordActivity parentActivity;
    @Bind(R.id.edt_password)
    public EditText edt_password;// 密码
    @Bind(R.id.edt_confirm_password)
    public EditText edt_confirm_password;// 确认密码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_set_password, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (SetPasswordActivity) getActivity();
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
    }

    /**
     * 确定
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_confirm)
    void confirm(View view) {
        Intent intent = new Intent(getActivity(), CompleteActivity.class);
        intent.putExtra("mode", NoteAPI.Mode.SET_PASSWORD);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}