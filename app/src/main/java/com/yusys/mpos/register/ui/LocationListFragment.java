package com.yusys.mpos.register.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.register.adapter.LocationAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 地址信息列表界面
 * parent是RegisterActivity,注册流程第7步的子界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-26 09:43
 */
public class LocationListFragment extends BaseFragment {

    private View fragmentView;
    private RegisterActivity parentActivity;

    @Bind(R.id.lv_location)
    ListView lv_location;
    public LocationAdapter adapter;
    public ArrayList<String> locationArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_location_list, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RegisterActivity) getActivity();
            if (locationArray == null) {
                locationArray = new ArrayList<>();
            }
            adapter = new LocationAdapter(this, locationArray);
            lv_location.setAdapter(adapter);
            lv_location.setOnItemClickListener(itemClickListener);
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
        parentActivity.toolbar_title.setText("选择地址");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(6));
            }
        });
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String location = locationArray.get(position);
            LocationFragment fragment = (LocationFragment) parentActivity.fragments.get(6);
            fragment.tv_location.setText(location);
            parentActivity.showFragment(fragment);
        }
    };
}