package com.yusys.mpos.pay;

/**
 * 支付模块接口和参数
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-23 15:30
 */
public class PayAPI {

    public interface Preferences {
        String FILENAME = "paypref";
        String DEFAULT_DEVICE_NAME = "defaultdevicename";
        String DEFAULT_BLUETOOTH_ADDRESS = "defaultbluetoothaddress";
    }
}
