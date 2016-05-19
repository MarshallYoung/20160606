package com.yusys.mpos.pay.manager;

import com.pax.mposapi.BluetoothController;

/**
 * 百富移动支付终端管理
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-18 17:16
 */
public class PaxManager {

    private PaxManager mInstance;
    private BluetoothController bluetoothController;// 蓝牙管理器

    private PaxManager() {
    }

    /**
     * 单例
     */
    public PaxManager getInstance() {
        if (mInstance == null) {
            mInstance = new PaxManager();
        }
        return mInstance;
    }
}