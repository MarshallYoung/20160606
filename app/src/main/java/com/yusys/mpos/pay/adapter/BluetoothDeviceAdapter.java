package com.yusys.mpos.pay.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yusys.mpos.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 蓝牙设备适配器,DeviceListFragment
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-21 12:34
 */
public class BluetoothDeviceAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<BluetoothDevice> list;

    public BluetoothDeviceAdapter(Activity activity) {
        super();
        this.activity = activity;
        list = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    private BluetoothDeviceAdapter() {
    }

    /**
     * 清空列表
     */
    public void clear() {
        list.clear();
        this.notifyDataSetChanged();
    }

    /**
     * 添加设备信息
     *
     * @param deviceInfo 蓝牙设备信息
     */
    public void addDevice(BluetoothDevice deviceInfo) {
        list.add(deviceInfo);
        this.notifyDataSetChanged();
    }

    public BluetoothDevice getDeviceInfo(int position) {
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
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.item_bluetooth, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_deviceName.setText(list.get(position).getName());
        viewHolder.tv_bluetoothAddress.setText(list.get(position).getAddress());
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.tv_device_name)
        TextView tv_deviceName;
        @Bind(R.id.tv_bluetooth_address)
        TextView tv_bluetoothAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}