package com.yusys.mpos.pay.ui;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseActivity;
import com.yusys.mpos.pay.PayAPI;
import com.yusys.mpos.pay.pax.PaxMainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-21 11:16
 */
public class PayActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.edt_amount)
    EditText edt_amount;// 金额
    @Bind(R.id.edt_bluetooth_status)
    EditText edt_bluetoothStatus;// 蓝牙状态
    @Bind(R.id.edt_device)
    EditText edt_device;// 当前设备
    @Bind(R.id.edt_bluetooth_address)
    EditText edt_bluetoothAddress;// 当前蓝牙
    @Bind(R.id.edt_default_device)
    EditText edt_defaultDevice;// 默认设备
    @Bind(R.id.edt_default_bluetooth)
    EditText edt_defaultBluetooth;// 默认蓝牙地址

    private double amount;
    private SharedPreferences preferences;
    private BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        amount = getIntent().getDoubleExtra(PayAPI.Payment.AMOUNT, 0);// 得到金额
        preferences = getSharedPreferences(PayAPI.Preferences.FILENAME, MODE_PRIVATE);
        initView();
    }

    private void initView() {
        toolbar_title.setText("选择机具");
        String amountString = String.format("%.2f", amount);
        edt_amount.setText(amountString);
        // 检查是否有默认蓝牙设备
        String name = preferences.getString(PayAPI.Preferences.DEFAULT_DEVICE_NAME, "");
        String address = preferences.getString(PayAPI.Preferences.DEFAULT_BLUETOOTH_ADDRESS, "");
        if (!name.isEmpty() && !address.isEmpty()) {// 有默认设备且有蓝牙地址
            edt_defaultDevice.setText(name);
            edt_defaultBluetooth.setText(address);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case PayAPI.ResultCode.SUCCESS:// 成功
                device = (BluetoothDevice) data.getExtras().get(PayAPI.Bluetooth.DEVICE_INFO);
                edt_device.setText(device.getName());
                edt_bluetoothAddress.setText(device.getAddress());
                break;
            case PayAPI.ResultCode.FAILED:// 失败
                break;
        }
    }

    /**
     * 蓝牙列表
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_bluetooth_list)
    void bluetoothList(View view) {
        Intent intent = new Intent(this, DeviceListFragment.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    /**
     * 选择蓝牙设备品牌
     */
    @SuppressWarnings("unused")
    @OnClick({R.id.btn_landi, R.id.btn_pax})
    void searchBluetooth(View view) {
        switch (view.getId()) {
            case R.id.btn_landi:// 联迪
                redirectToSearchBluetooth(PayAPI.DeviceType.LANDI);
                break;
            case R.id.btn_pax:// 百富
                redirectToSearchBluetooth(PayAPI.DeviceType.PAX);
                break;
        }
    }

    /**
     * 搜索蓝牙
     *
     * @param requestCode 搜索何种品牌的蓝牙设备
     */
    private void redirectToSearchBluetooth(int requestCode) {
        Class cls = null;
        switch (requestCode) {
            case PayAPI.DeviceType.LANDI:
                cls = LandiActivity.class;
                break;
            case PayAPI.DeviceType.PAX:
                cls = PaxMainActivity.class;
                break;
        }
        Intent intent = new Intent(this, cls);
        intent.putExtra(PayAPI.Payment.AMOUNT, amount);
        intent.putExtra(PayAPI.Bluetooth.DEVICE_INFO, device);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_back)
    void back(View view) {
        finish();
    }
}