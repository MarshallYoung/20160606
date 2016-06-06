package com.yusys.mpos.main.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.BroadcastAPI;
import com.yusys.mpos.pay.PayAPI;
import com.yusys.mpos.pay.ui.ConnectionActivity;
import com.yusys.mpos.pay.ui.PayActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收款
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-06 17:26
 */
public class GatheringFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar_connect)
    LinearLayout toolbar_connect;// 连接按钮
    @Bind(R.id.toolbar_connect_text)
    TextView toolbar_connectText;// 连接按钮文字
    @Bind(R.id.calculator_result)
    TextView tv_result;// 结果
    @Bind(R.id.ll_spinner)
    LinearLayout ll_spinner;// 下拉列表

    private double amount;// 金额
    private int connectionStatus;// 连接状态
    private BluetoothAdapter bluetoothAdapter;

    /**
     * 接收到连接蓝牙设备的广播
     */
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BroadcastAPI.BLUETOOTH_DISCONNECTED:// 未连接
                    toolbar_connectText.setText("未连接");
                    break;
                case BroadcastAPI.BLUETOOTH_CONNECTED:// 已连接
                    toolbar_connectText.setText("已连接");
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_gathering, container, false);
            ButterKnife.bind(this, fragmentView);
            toolbar_title.setText("收款");
            amount = 0;
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            IntentFilter filter = new IntentFilter();
            filter.addAction(BroadcastAPI.BLUETOOTH_DISCONNECTED);
            filter.addAction(BroadcastAPI.BLUETOOTH_CONNECTED);
            getActivity().registerReceiver(bluetoothReceiver, filter);
            if (bluetoothAdapter == null) {// 无蓝牙功能
                toolbar_connect.setEnabled(false);
                toolbar_connectText.setText("手机不支持蓝牙");
            }
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        ll_spinner.setVisibility(View.INVISIBLE);// 切换界面,隐藏下拉列表
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        getActivity().unregisterReceiver(bluetoothReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 数字按钮的执行方法
     */
    @SuppressWarnings("unused")
    @OnClick({R.id.calculator_0, R.id.calculator_1, R.id.calculator_2, R.id.calculator_3,
            R.id.calculator_4, R.id.calculator_5, R.id.calculator_6,
            R.id.calculator_7, R.id.calculator_8, R.id.calculator_9})
    void numberClicked(View view) {
        String tvString = tv_result.getText().toString().trim();
        if (tvString.length() < 12) {// 最大金额千万级
            String text = ((Button) view).getText().toString();
            int value = Integer.valueOf(text);
            amount = amount * 10 + value * 0.01;
            String result = String.format("%.2f", amount);
            tv_result.setText(result);
        }
    }

    /**
     * 清除
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.calculator_c)
    void clear(View view) {
        tv_result.setText("0.00");
        amount = 0;
    }

    /**
     * 退格
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.calculator_back)
    void delete(View view) {
        String string = String.format("%.3f", amount / 10);
        String result = string.substring(0, string.length() - 1);// 去掉最后一位
        amount = Double.parseDouble(result);
        tv_result.setText(result);
    }

    /**
     * 刷卡
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_swiping_card)
    void gathering(View view) {
        if (connectionStatus == PayAPI.Bluetooth.DISCONNECTED) {

        } else if (connectionStatus == PayAPI.Bluetooth.CONNECTED) {

        }
        if (amount > 0) {// 金额正常
            Intent payIntent = new Intent(getActivity(), PayActivity.class);
            payIntent.putExtra(PayAPI.Payment.AMOUNT, amount);
            startActivity(payIntent);
            getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            clear(null);//清理金额
        } else {// 金额非法
            Toast.makeText(getActivity(), "请输入正确的金额", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 连接
     */
    @SuppressWarnings("unused")
    @OnClick({R.id.toolbar_connect, R.id.ll_device_settings})
    void connect(View view) {
        if (connectionStatus == PayAPI.Bluetooth.DISCONNECTED) {// 未连接
            Intent intent = new Intent(getActivity(), ConnectionActivity.class);
            startActivityForResult(intent, PayAPI.RequestCode.BLUETOOTH);
            getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        } else if (connectionStatus == PayAPI.Bluetooth.CONNECTED) {// 已连接

        }
    }

    /**
     * 显示/隐藏,下拉列表
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_add)
    void operateSpinner(View view) {
        switch (ll_spinner.getVisibility()) {
            case View.INVISIBLE:
                ll_spinner.setVisibility(View.VISIBLE);
                break;
            case View.VISIBLE:
                ll_spinner.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * 下拉列表功能
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.v_gray)
    void spinnerClicked(View view) {
        switch (view.getId()) {
            case R.id.v_gray:// 灰色区域
                ll_spinner.setVisibility(View.INVISIBLE);
                break;
        }
    }
}