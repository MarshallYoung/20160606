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

    public interface Payment {
        String AMOUNT = "amount";
    }

    public interface Bluetooth {
        int DISCONNECTED = 0x00;// 未连接
        int CONNECTED = 0x01;// 已连接
        String DEVICE_INFO = "deviceinfo";
    }

    public interface DeviceType {
        int LANDI = 0xff01;// 联迪
        int PAX = 0xff02;// 百富
    }

    public interface RequestCode {
        int BLUETOOTH = 0xf0;
    }

    public interface ResultCode {
        int FAILED = 0x00;
        int SUCCESS = 0x01;
    }
}
