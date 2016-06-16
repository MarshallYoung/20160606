package com.yusys.mpos.pay.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.pay.adapter.BluetoothDeviceAdapter;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 蓝牙设备列表
 * parent是ConnectionActivity
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-21 11:03
 */
public class DeviceListFragment extends BaseFragment {

    private View fragmentView;
    private ConnectionActivity parentActivity;
    @Bind(R.id.tv_note)
    TextView tv_note;
    @Bind(R.id.lv_bluetooth)
    ListView lv_bluetooth;

    private Timer timer;
    private TimerTask task;
    private int i = 0;
    private BluetoothAdapter bluetoothAdapter;// 蓝牙搜索适配器
    private BluetoothDeviceAdapter deviceAdapter;// 设备适配器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_device_list, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (ConnectionActivity) getActivity();
            timer = new Timer();
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            deviceAdapter = new BluetoothDeviceAdapter(getActivity());
            lv_bluetooth.setAdapter(deviceAdapter);
            lv_bluetooth.setOnItemClickListener(itemClickListener);
            // 注册用以接收到已搜索到的蓝牙设备的receiver
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            getActivity().registerReceiver(bluetoothReceiver, filter);
            // 注册搜索完时的receiver
            filter = new IntentFilter(android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            getActivity().registerReceiver(bluetoothReceiver, filter);
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
        parentActivity.toolbar_title.setText("设备搜索");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(0));
            }
        });
        searchDevice(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopSearching(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(bluetoothReceiver);
    }

    /**
     * 搜索蓝牙设备
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_research)
    void searchDevice(View view) {
        deviceAdapter.clear();
        if (bluetoothAdapter.isDiscovering()) {// 如果正在搜索,那么停止搜索
            stopSearching(null);
        }
        progressText("搜索中");
        bluetoothAdapter.startDiscovery();
    }

    /**
     * 停止搜索
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_stop_search)
    void stopSearching(View view) {
        task.cancel();
        // 如果正在搜索，就先取消搜索
        if (bluetoothAdapter.isDiscovering()) {
            tv_note.setText("停止搜索");
            bluetoothAdapter.cancelDiscovery();
        }
    }

    /**
     * 接收到发现蓝牙设备的通知
     */
    private BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 获得已经搜索到的蓝牙设备
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 搜索到的不是已经绑定的蓝牙设备
                deviceAdapter.addDevice(device);
            } else if (action.equals(android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {// 搜索完成
                task.cancel();
                tv_note.setText("搜索完成");
            }
        }
    };

    /**
     * 蓝牙设备点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), LandiActivity.class);
            BluetoothDevice device = deviceAdapter.getDeviceInfo(position);
            intent.putExtra("deviceinfo", device);
            getActivity().startActivity(intent);
        }
    };

    /**
     * 显示进度提示的文本
     */
    private void progressText(final String text) {
        if (task != null) {
            task.cancel();
        }
        i = 0;
        task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (i % 2 == 1) {
                            tv_note.setText(text + "...");
                        } else if (i % 2 == 0) {
                            tv_note.setText(text + "..");
                        }
                    }
                });
                i++;
            }
        };
        timer.schedule(task, 800, 800);
    }
}