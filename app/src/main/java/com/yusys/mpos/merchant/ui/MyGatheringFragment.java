package com.yusys.mpos.merchant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.merchant.adapter.MyGatheringAdapter;
import com.yusys.mpos.merchant.bean.GatheringInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的收款界面
 * parent是MyGatheringActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class MyGatheringFragment extends BaseFragment {

    private View fragmentView;
    private MyGatheringActivity parentActivity;

    @Bind(R.id.lv_my_gathering)
    ListView lv_myGathering;
    private MyGatheringAdapter adapter;
    private ArrayList<GatheringInfo> gatheringInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_my_gathering, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (MyGatheringActivity) getActivity();
            gatheringInfos = new ArrayList();
            // 添加三条数据
            gatheringInfos.add(new GatheringInfo());
            gatheringInfos.add(new GatheringInfo());
            gatheringInfos.add(new GatheringInfo());
            adapter = new MyGatheringAdapter(this, gatheringInfos);
            lv_myGathering.setAdapter(adapter);
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
        parentActivity.toolbar_title.setText("我的收款");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.finish();
            }
        });
        parentActivity.toolbar_right_button.setVisibility(View.VISIBLE);
        parentActivity.toolbar_right_button.setText("到账查询");
        parentActivity.toolbar_right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(1));
            }
        });
    }
}