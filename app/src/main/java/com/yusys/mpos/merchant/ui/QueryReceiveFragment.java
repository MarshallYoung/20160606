package com.yusys.mpos.merchant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.merchant.adapter.QueryReceiveAdapter;
import com.yusys.mpos.merchant.bean.ReceiveInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 到账查询界面
 * parent是MyGatheringActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-10 11:29
 */
public class QueryReceiveFragment extends BaseFragment {

    private View fragmentView;
    private MyGatheringActivity parentActivity;

    @Bind(R.id.lv_query_receive)
    ListView lv_queryReceive;
    private QueryReceiveAdapter adapter;
    private ArrayList<ReceiveInfo> receiveInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_query_receive, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (MyGatheringActivity) getActivity();
            receiveInfos = new ArrayList();
            // 添加三条数据
            receiveInfos.add(new ReceiveInfo());
            receiveInfos.add(new ReceiveInfo());
            adapter = new QueryReceiveAdapter(this, receiveInfos);
            lv_queryReceive.setAdapter(adapter);
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
        parentActivity.toolbar_title.setText("到账查询");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.myGatheringFragment);
            }
        });
        parentActivity.toolbar_right_button.setVisibility(View.INVISIBLE);
    }
}