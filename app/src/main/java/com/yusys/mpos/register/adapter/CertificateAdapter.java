package com.yusys.mpos.register.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.register.ui.CertificatesFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 证件照片的适配器
 * 用于CertificatesFragment的gridView
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-04 15:35
 */
public class CertificateAdapter extends BaseAdapter {

    public CertificatesFragment fragment;
    public ArrayList<CertificateItem> itemList;

    /**
     * 构造方法
     *
     * @param fragment gridView所在fragment
     * @param itemList 含有每个按钮封装信息的列表
     */
    public CertificateAdapter(CertificatesFragment fragment, ArrayList<CertificateItem> itemList) {
        this.fragment = fragment;
        this.itemList = itemList;
    }

    @SuppressWarnings("unused")
    private CertificateAdapter() {
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
            convertView = inflater.inflate(R.layout.item_take_certificate, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CertificateItem item = itemList.get(position);
        if (item != null) {
            viewHolder.iv_certificate_photo.setBackgroundResource(item.image);
            viewHolder.tv_certificate_note.setText(item.text);
            viewHolder.ib_take_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.takePhoto(position);
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.iv_certificate_photo)
        ImageView iv_certificate_photo;
        @Bind(R.id.tv_certificate_note)
        TextView tv_certificate_note;
        @Bind(R.id.ib_take_photo)
        ImageButton ib_take_photo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}