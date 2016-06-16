package com.yusys.mpos.register.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.register.ui.LocationListFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 地址的适配器
 * 用于LocationListFragment的ListView
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-04 15:35
 */
public class LocationAdapter extends BaseAdapter {

    public LocationListFragment fragment;
    public ArrayList<String> itemList;

    /**
     * 构造方法
     *
     * @param fragment ListView所在fragment
     * @param itemList 含有每个按钮封装信息的列表
     */
    public LocationAdapter(LocationListFragment fragment, ArrayList<String> itemList) {
        this.fragment = fragment;
        this.itemList = itemList;
    }

    @SuppressWarnings("unused")
    private LocationAdapter() {
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(fragment.getActivity());
            convertView = inflater.inflate(R.layout.item_location, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String location = itemList.get(position);
        if (location != null) {
            viewHolder.tv_location.setText(location);
        }
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.tv_location)
        TextView tv_location;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}