package com.yusys.mpos.pay.manager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.landicorp.android.mpos.reader.LandiMPos;

/**
 * 联迪移动支付终端管理
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-18 17:12
 */
public class LandiManager {

    private LandiManager mInstance;
    private Context context;// App上下文
    private LandiMPos mPos;

    private LandiManager() {
    }

    /**
     * 单例
     */
    public LandiManager getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new LandiManager();
            context = activity.getApplicationContext();
            mPos = LandiMPos.getInstance(context);
        }
        return mInstance;
    }
}