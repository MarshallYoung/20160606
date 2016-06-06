package com.yusys.mpos.merchant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yusys.mpos.R;
import com.yusys.mpos.merchant.bean.ReceiveInfo;
import com.yusys.mpos.merchant.ui.QueryReceiveFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * 到账查询适配器,QueryReceiveFragment
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-21 12:34
 */
public class QueryReceiveAdapter extends BaseAdapter {

    private QueryReceiveFragment fragment;
    private ArrayList<ReceiveInfo> list;

    public QueryReceiveAdapter(QueryReceiveFragment fragment, ArrayList<ReceiveInfo> list) {
        super();
        this.fragment = fragment;
        this.list = list;
    }

    @SuppressWarnings("unused")
    private QueryReceiveAdapter() {
    }

    /**
     * 清空列表
     */
    public void clear() {
        list.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 添加到账信息
     *
     * @param receiveInfo 到账信息
     */
    public void addDevice(ReceiveInfo receiveInfo) {
        list.add(receiveInfo);
        this.notifyDataSetChanged();
    }

    public ReceiveInfo getDeviceInfo(int position) {
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
            convertView = inflater.inflate(R.layout.item_query_receive, null);
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