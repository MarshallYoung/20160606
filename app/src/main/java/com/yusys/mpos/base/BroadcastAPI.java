package com.yusys.mpos.base;

/**
 * 广播接口
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-25 17:37
 */
public class BroadcastAPI {
    /**
     * 未连接
     */
    public static final String BLUETOOTH_DISCONNECTED = "com.yusys.mpos.BluetoothDisconnected";
    /**
     * 正在连接
     */
    public static final String BLUETOOTH_CONNECTING = "com.yusys.mpos.BluetoothConnecting";
    /**
     * 已连接
     */
    public static final String BLUETOOTH_CONNECTED = "com.yusys.mpos.BluetoothConnected";
}