package com.yusys.mpos.creditcard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.note.ui.CompleteActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 刷卡界面
 * parent是RefundActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class SwipingCardFragment extends BaseFragment {

    private View fragmentView;
    private RefundActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_swiping_card, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RefundActivity) getActivity();
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
        parentActivity.toolbar_title.setText("请刷卡或插入IC卡");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.fragments.get(1);
            }
        });
    }

    /**
     * 确定
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_confirm)
    void confirm() {
        Intent intent = new Intent(getActivity(), CompleteActivity.class);
        intent.putExtra("mode", 0x02);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}