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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能列表的适配器,用于functionFragment的gridView
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-04 15:35
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

    @SuppressWarnings("unused")
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_functionbutton, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FunctionItem item = itemList.get(position);
        if (item != null) {
            viewHolder.function_image.setImageResource(item.image);
            viewHolder.function_text.setText(item.text);
        }
        return convertView;
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView textView;

        @Bind(R.id.function_image)
        ImageView function_image;
        @Bind(R.id.function_text)
        TextView function_text;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}