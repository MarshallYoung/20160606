package com.yusys.mpos.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yusys.mpos.R;

import java.util.ArrayList;

/**
 * 功能列表的适配器,用于functionFragment的gridView
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-04 15:35
 */
public class FunctionAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<FunctionItem> itemList;

    /**
     * 构造方法
     *
     * @param context  gridView所在Activity(上下文)
     * @param itemList 含有每个按钮封装信息的列表
     */
    public FunctionAdapter(Context context, ArrayList<FunctionItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    private FunctionAdapter() {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_functionbutton, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.function_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.function_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FunctionItem item = itemList.get(position);
        if (item != null) {
            viewHolder.imageView.setImageResource(item.image);
            viewHolder.textView.setText(item.text);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

}