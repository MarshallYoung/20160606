package com.yusys.mpos.merchant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yusys.mpos.R;
import com.yusys.mpos.merchant.bean.GatheringInfo;
import com.yusys.mpos.merchant.ui.MyGatheringFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 我的收款适配器,MyGatheringFragment
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-21 12:34
 */
public class MyGatheringAdapter extends BaseAdapter {

    private MyGatheringFragment fragment;
    private ArrayList<GatheringInfo> list;

    public MyGatheringAdapter(MyGatheringFragment fragment, ArrayList<GatheringInfo> list) {
        super();
        this.fragment = fragment;
        this.list = list;
    }

    @SuppressWarnings("unused")
    private MyGatheringAdapter() {
    }

    /**
     * 清空列表
     */
    public void clear() {
        list.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 添加收款信息
     *
     * @param gatheringInfo 收款信息
     */
    public void addDevice(GatheringInfo gatheringInfo) {
        list.add(gatheringInfo);
        this.notifyDataSetChanged();
    }

    public GatheringInfo getDeviceInfo(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(fragment.getActivity());
            convertView = inflater.inflate(R.layout.item_my_gathering, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}