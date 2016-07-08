package com.yusys.mpos.pay.ui;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.landicorp.android.mpos.newReader.LandiReader;
import com.landicorp.android.mpos.newReader.MposCardInfo;
import com.landicorp.android.mpos.newReader.PublicInterface;
import com.landicorp.android.mpos.newReader.PublicInterface.ConnectDeviceListener;
import com.landicorp.mpos.reader.BasicReaderListeners;
import com.landicorp.mpos.util.StringUtil;
import com.landicorp.robert.comm.api.CommunicationManagerBase;
import com.landicorp.robert.comm.api.DeviceInfo;
import com.yusys.mpos.R;
import com.yusys.mpos.base.BroadcastAPI;
import com.yusys.mpos.base.YXApplication;
import com.yusys.mpos.base.manager.LogUtil;
import com.yusys.mpos.base.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 联迪界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-20 10:11
 */
public class LandiActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.edt_amount)
    EditText edt_amount;
    @Bind(R.id.edt_bluetooth_address)
    EditText edt_bluetoothAddress;
    @Bind(R.id.edt_connect_status)
    EditText edt_connectStatus;
    SweetAlertDialog dialog;

    private BluetoothDevice device;
    private LandiReader reader;
    private ReceiveBroadCast receiceBrocast;

    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "收到断开蓝牙的通知",
                    Toast.LENGTH_LONG).show();
            System.out.println();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landi);
        ButterKnife.bind(this);
        reader = ((YXApplication) getApplication()).landiReader;
        if (device == null) {// 设备是空的话就会退出
            finish();
        }
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reader = null;
    }

    private void initView() {
        toolbar_title.setText("联迪");
        edt_bluetoothAddress.setText(device.getAddress());
    }

    /**
     * 连接设备
     */
    @OnClick(R.id.btn_connect)
    void connectDevice() {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("正在连接设备...");
        dialog.setCancelable(false);
        dialog.show();
        LogUtil.e("==蓝牙地址==", device.getAddress());
        CommunicationManagerBase.CommunicationMode openMode = CommunicationManagerBase.CommunicationMode.MODE_DUPLEX;
        DeviceInfo info = new DeviceInfo();
        info.setIdentifier(device.getAddress());
        info.setName(device.getName());
        info.setDevChannel(CommunicationManagerBase.DeviceCommunicationChannel.BLUETOOTH);
        reader.connectDeviceWithBluetooth(info.getIdentifier(),
                new ConnectDeviceListener() {
                    @Override
                    public void deviceDisconnect() {
                        edt_connectStatus.setText("未连接");
                        sendBroadcast(new Intent(BroadcastAPI.BLUETOOTH_DISCONNECTED));
                        dialog.cancel();
                        Toast.makeText(LandiActivity.this, "设备已断开连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void connectSuccess() {
                        edt_connectStatus.setText("已连接");
                        sendBroadcast(new Intent(BroadcastAPI.BLUETOOTH_CONNECTED));
                        dialog.cancel();
                    }

                    @Override
                    public void connectFailed(String errorMesg) {
                        edt_connectStatus.setText("未连接");
                        sendBroadcast(new Intent(BroadcastAPI.BLUETOOTH_DISCONNECTED));
                        dialog.cancel();
                        Toast.makeText(LandiActivity.this, "设备连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 关闭连接
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_close_connection)
    void closeConnection(View view) {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("正在关闭连接...");
        dialog.setCancelable(false);
        dialog.show();
        reader.closeDevice();
        edt_connectStatus.setText("未连接");
        dialog.cancel();
    }

    /**
     * 主密钥
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_main_key)
    void mainKey(View view) {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("下主密钥...");
        dialog.setCancelable(false);
        dialog.show();
        boolean flag = reader.loadKey(PublicInterface.KeyType.MASTER_KEY,
                StringUtil.hexStringToBytes("11111111111111111111111111111111"),
                StringUtil.hexStringToBytes("82E13665"));
        if (flag) {
            dialog.cancel();
            Toast.makeText(LandiActivity.this, "主密钥导入成功", Toast.LENGTH_SHORT).show();
        } else {
            dialog.cancel();
            Toast.makeText(LandiActivity.this, "主密钥导入失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 签到
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_sign_in)
    void signIn(View view) {
        boolean flag1 = reader.loadKey(PublicInterface.KeyType.MAC_KEY,
                StringUtil.hexStringToBytes("F40379AB9E0EC533F40379AB9E0EC533"),
                StringUtil.hexStringToBytes("82E13665"));
        if (flag1) {
            Toast.makeText(LandiActivity.this, "MAC密钥导入成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LandiActivity.this, "MAC密钥导入失败", Toast.LENGTH_SHORT).show();
        }
        boolean flag2 = reader.loadKey(PublicInterface.KeyType.TDK_KEY,
                StringUtil.hexStringToBytes("F40379AB9E0EC533F40379AB9E0EC533"),
                StringUtil.hexStringToBytes("82E13665"));
        if (flag2) {
            Toast.makeText(LandiActivity.this, "磁道密钥导入成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LandiActivity.this, "磁道密钥导入失败", Toast.LENGTH_SHORT).show();
        }
        boolean flag3 = reader.loadKey(PublicInterface.KeyType.PIN_KEY,
                StringUtil.hexStringToBytes("F40379AB9E0EC533F40379AB9E0EC533"),
                StringUtil.hexStringToBytes("82E13665"));
        if (flag3) {
            Toast.makeText(LandiActivity.this, "PIN密钥导入成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LandiActivity.this, "PIN密钥导入失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 开始交易
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_start_transaction)
    void startTransaction(View view) {
        reader.readCard(4100, 30, "宇信", true, true, readCardListener);
    }

    PublicInterface.ReadCardListener readCardListener = new PublicInterface.ReadCardListener() {
        @Override
        public void notifyReadCard() {

        }

        @Override
        public void notifyReadCardAndLowPower() {

        }

        @Override
        public void notifyInputPin() {

        }

        @Override
        public void reswipeCard() {

        }

        @Override
        public void insertICcard() {

        }

        @Override
        public void failReadCard() {

        }

        @Override
        public void failReadPin() {

        }

        @Override
        public void operaTimeOut() {

        }

        @Override
        public void cancelReadCard() {

        }

        @Override
        public void cancelInputPin() {

        }

        @Override
        public void requestInputPin(MposCardInfo mposCardInfo) {

        }

        @Override
        public void readCardSuccess(MposCardInfo cardInfo) {
            String tString = "是";
            if (cardInfo.getCardType() == BasicReaderListeners.CardType.IC_CARD) {
                tString += "IC卡";
            } else if (cardInfo.getCardType() == BasicReaderListeners.CardType.RF_CARD) {
                tString += "非接卡";
            } else {
                tString += "磁条卡";
            }
            tString += "\n金额是:" + cardInfo.getAmount();
            tString += "\n有效期到" + cardInfo.getCardExpDate() + "\n";
            tString += "卡号" + cardInfo.getAccount() + "\n";
            if (cardInfo.getTrack1Data() != null
                    && cardInfo.getTrack1Data().length() > 0) {
                tString += "一磁道:" + cardInfo.getTrack1Data();
            }
            if (cardInfo.getTrack2Data() != null
                    && cardInfo.getTrack2Data().length() > 0) {
                tString += "二磁道:" + cardInfo.getTrack2Data();
            }
            if (cardInfo.getTrack3Data() != null
                    && cardInfo.getTrack3Data().length() > 0) {
                tString += "三磁道:" + cardInfo.getTrack3Data() + "\n";
            }
            if (cardInfo.getIcCardSeqNumber() != null
                    && cardInfo.getIcCardSeqNumber().length() > 0) {
                tString += "IC卡序列号" + cardInfo.getIcCardSeqNumber() + "\n";
            }
            if (cardInfo.getEncrypPin() != null
                    && cardInfo.getEncrypPin().length > 0) {
                tString += "PIN = "
                        + StringUtil.bytesToHexString(cardInfo.getEncrypPin(),
                        cardInfo.getEncrypPin().length) + "\n";
            }
            if (cardInfo.getIcTag55Data() != null && cardInfo.getIcTag55Data().length > 0) {
                tString += "IC卡55域数据："
                        + StringUtil.bytesToHexString(cardInfo.getIcTag55Data(),
                        cardInfo.getIcTag55Data().length);
            }
            LogUtil.e("==mPos返回信息==", tString);
        }

        @Override
        public void emvSecondIssuanceSuccess(MposCardInfo mposCardInfo) {

        }

        @Override
        public void emvSecondIssuanceFail(MposCardInfo mposCardInfo) {

        }
    };

    /**
     * 取消当前交易
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_cancel)
    void cancel(View view) {
        reader.cancelTrade();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.toolbar_back)
    void back(View view) {
        finish();
    }
}