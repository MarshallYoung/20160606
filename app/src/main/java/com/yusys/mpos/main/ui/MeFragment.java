package com.yusys.mpos.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.login.ui.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-04-06 17:26
 */
public class MeFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_me,
                    container, false);
            ButterKnife.bind(this, fragmentView);
            toolbar_title.setText("我");
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

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_exit)
    void exit(View view) {
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        getActivity().finish();
    }

}