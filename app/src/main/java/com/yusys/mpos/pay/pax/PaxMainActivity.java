package com.yusys.mpos.pay.pax;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.pax.mposapi.BluetoothController;
import com.pax.mposapi.BluetoothController.BluetoothSearchListener;
import com.pax.mposapi.BluetoothController.BluetoothStateListener;
import com.pax.mposapi.DeviceInfo;
import com.pax.mposapi.EnumBtErrorCode;
import com.pax.mposapi.EnumCommand;
import com.pax.mposapi.GetPinInfo;
import com.pax.mposapi.MPosApi;
import com.pax.mposapi.MposDevice;
import com.pax.mposapi.OpenReader;
import com.pax.mposapi.PBOCAuthInfo;
import com.pax.mposapi.ReadCardNo;
import com.pax.mposapi.ReadECInfo;
import com.pax.mposapi.ReadMagCardNo;
import com.pax.mposapi.ReadPlainCardNo;
import com.pax.mposapi.StartPBOC;
import com.pax.mposapi.TradeListener;
import com.pax.mposapi.UpdateAID;
import com.pax.mposapi.UpdateCA;
import com.yusys.mpos.R;

public class PaxMainActivity extends Activity {

    MPosApi mApi = MPosApi.getInstance();
    Button mButton3;
    Button mButton4;
    Button mButton5;
    Button mButton6;
    Button mButton7;
    Button mButton8;
    Button mButton9;
    Button mButton10;
    Button mButton11;
    PaxMainActivity mActivity;
    MPosApi posApi = MPosApi.getInstance();
    BluetoothController mBtCtrl = BluetoothController.getInstance();
    Button mButton51;
    Button mButton61;

    BluetoothStateListener mBtStateListener = new BluetoothStateListener() {

        @Override
        public void onStateChange(int state) {
            Log.e("BluetoothStateListener", String.valueOf(state));
            if (state == BluetoothController.STATE_ENABLE)
                Toast.makeText(mActivity, "蓝牙打开", Toast.LENGTH_SHORT).show();
            else if (state == BluetoothController.STATE_DISABLE)
                Toast.makeText(mActivity, "蓝牙关闭", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "蓝牙位置", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pax_main);
        mActivity = this;
        /************* 设置上下文关联 ********************************/
        mBtCtrl.setContext(mActivity);
        /*********** 设置蓝牙状态监听 *******************/
        mBtCtrl.setBtStateListener(mBtStateListener);
        /************** 蓝牙开关状态 **********************/
        MPosApi.getInstance().setContext(mActivity);
        ((Button) findViewById(R.id.button01))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mBtCtrl.isOpen()) {
                            Toast.makeText(mActivity, "蓝牙打开",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("btopen", "true");
                        } else {
                            Toast.makeText(mActivity, "蓝牙关闭",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("btopen", "false");
                        }

                    }

                });
        /************** 请求打开蓝牙设备 **********************/
        ((Button) findViewById(R.id.button02))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // 通过BluetoothStateListener返回结果
                        mBtCtrl.openBt(mActivity);
                    }
                });
        /************** 请求关闭蓝牙设备 **********************/
        ((Button) findViewById(R.id.button03))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // 通过BluetoothStateListener返回结果
                        mBtCtrl.closeBt();
                    }
                });
        /**************** 搜索蓝牙 ******************************/
        ((Button) findViewById(R.id.button51))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mBtCtrl.discovery(new BluetoothSearchListener() {

                            @Override
                            public void onSearchDevice(
                                    MposDevice paramMposDevice) {
                                // TODO Auto-generated method stub
                                Log.e("btinfo", paramMposDevice.getAddress()
                                        + "====" + paramMposDevice.getName());
                                Toast.makeText(
                                        mActivity,
                                        "蓝牙搜索:" + paramMposDevice.getAddress()
                                                + "===="
                                                + paramMposDevice.getName(),
                                        Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onStopSearch() {
                                // TODO Auto-generated method stub
                                Log.e("btsearch", "stop");
                                Runnable thread = new Runnable() {

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(mActivity, "蓝牙搜索停止",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                };
                                mActivity.runOnUiThread(thread);

                            }

                        });
                    }

                });
        /************** 停止搜索 **********************/
        ((Button) findViewById(R.id.button61))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mBtCtrl.stopDiscovery();
                    }

                });

        /*** 连接蓝牙 ******/
        ((Button) this.findViewById(R.id.button5))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new Thread() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                Runnable thread;
                                if (mApi.connect("8C:DE:52:74:05:6C")) {// 8C:DE:52:7B:6F:3B；8C:DE:52:81:35:C6
                                    thread = new Runnable() {
                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(mActivity, "蓝牙连接成功",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    };

                                } else {
                                    thread = new Runnable() {
                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(mActivity, "蓝牙连接失败",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                }

                                mActivity.runOnUiThread(thread);
                                // 8C:DE:52:3A:FF:7F
                                // mBtCtrl.pairDevice("8C:DE:52:7B:6F:3B");
                            }

                        }.start();
                    }

                });
        /*********** 关闭蓝牙连接 **************************/
        ((Button) this.findViewById(R.id.button6))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new Thread() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                Runnable thread;
                                if (mApi.disconnect()) {
                                    thread = new Runnable() {
                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(mActivity, "蓝牙关闭成功",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                } else {
                                    thread = new Runnable() {
                                        @Override
                                        public void run() {
                                            // TODO Auto-generated method stub
                                            Toast.makeText(mActivity, "蓝牙关闭失败",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                }
                                mActivity.runOnUiThread(thread);

                            }

                        }.start();
                    }

                });
        /********* 蓝牙连接状态 *****************/
        ((Button) findViewById(R.id.button611111))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (mApi.isConnected())
                            Toast.makeText(mActivity, "蓝牙状态:已连接",
                                    Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(mActivity, "蓝牙状态:未连接",
                                    Toast.LENGTH_SHORT).show();

                    }

                });
        /*************** initBluthPos *********************/

        ((Button) findViewById(R.id.button511))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.setTradeListener(new TradeListener() {

                            @Override
                            public void onProgress(EnumCommand cmd,
                                                   int progressCode, String message) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public <T> void onTransSucceed(EnumCommand cmd,
                                                           final T params) {
                                // TODO Auto-generated method stub
                                Runnable thread = null;
                                switch (cmd) {
                                    case GetDeviceInfo:
                                        DeviceInfo devInfo = (DeviceInfo) params;
                                        Log.e("deviceinfo", devInfo.getSn());
                                        final String info = "设备型号:"
                                                + devInfo.getDeviceType() + "\r\n"
                                                + "SN号:" + devInfo.getSn();
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity, info,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case Reset:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity, "复位成功",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case GetTime:

                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity,
                                                        "时间：" + (String) params,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        Log.e("getTime", (String) params);
                                        break;
                                    case SetTime:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity, "设置时间成功",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case GetRASRandomKey:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity,
                                                        "随机密钥：" + (String) params,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case OpenReader:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                OpenReader openreader = (OpenReader) params;

                                                Toast.makeText(
                                                        mActivity,
                                                        "读卡状态码:"
                                                                + openreader
                                                                .getStatus()
                                                                + "\r\n"
                                                                + "服务代码:"
                                                                + openreader
                                                                .getServiceCode(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case ReadCardNo:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                ReadCardNo readCardNo = (ReadCardNo) params;

                                                Toast.makeText(
                                                        mActivity,
                                                        "终端交易时间:"
                                                                + readCardNo
                                                                .getTradeTime()
                                                                + "\r\n"
                                                                + "随机数:"
                                                                + readCardNo
                                                                .getRandom()
                                                                + "\r\n"
                                                                + "加密数据:"
                                                                + readCardNo
                                                                .getEncData()
                                                                + "\r\n",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case ReadMagCardNo:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                ReadMagCardNo readMagCardNo = (ReadMagCardNo) params;

                                                Toast.makeText(
                                                        mActivity,
                                                        "终端交易时间:"
                                                                + readMagCardNo
                                                                .getTradeTime()
                                                                + "\r\n"
                                                                + "随机数:"
                                                                + readMagCardNo
                                                                .getRandom()
                                                                + "\r\n"
                                                                + "主账号:"
                                                                + readMagCardNo
                                                                .getMainAccount()
                                                                + "\r\n",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case GetPin:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                GetPinInfo pinInfo = (GetPinInfo) params;
                                                String pinStr = "输密状态码:  "
                                                        + pinInfo.getStatus()
                                                        + "\r\n" + "PINBLOCK密文:  "
                                                        + pinInfo.getPinBlock()
                                                        + "\r\n" + "金额密文:  "
                                                        + pinInfo.getAmountenc()
                                                        + "\r\n" + "MAC:  "
                                                        + pinInfo.getMac() + "\r\n";

                                                Toast.makeText(mActivity, pinStr,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case UpdateCA:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                if (params == null)
                                                    Toast.makeText(mActivity,
                                                            "updateca成功",
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                else {
                                                    UpdateCA updateca = (UpdateCA) params;
                                                    Toast.makeText(
                                                            mActivity,
                                                            "updateca"
                                                                    + String.valueOf(updateca
                                                                    .getRidNum()),
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }
                                        };

                                        break;
                                    case UpdateAID:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                if (params == null)
                                                    Toast.makeText(mActivity,
                                                            "updateca成功",
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                else {
                                                    UpdateAID updateAid = (UpdateAID) params;
                                                    Toast.makeText(
                                                            mActivity,
                                                            "updateca"
                                                                    + String.valueOf(updateAid
                                                                    .getAidNum()),
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }
                                        };
                                        break;
                                    case StartPBOC:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                StartPBOC startPBOCInfo = (StartPBOC) params;
                                                String pinStr = "执行结果:  "
                                                        + startPBOCInfo.getResult()
                                                        + "\r\n"
                                                        + "随机数:  "
                                                        + startPBOCInfo.getRandom()
                                                        + "\r\n"
                                                        + "输密状态码:  "
                                                        + startPBOCInfo.getStatus()
                                                        + "\r\n"
                                                        +
                                                        // "PINBLOCK密文:  "+startPBOCInfo.getPinBlock()+"\r\n"+
                                                        "MAC:  "
                                                        + startPBOCInfo
                                                        .getCustMac()
                                                        + "\r\n";

                                                Toast.makeText(mActivity, pinStr,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case StartPBOCAuthor:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                PBOCAuthInfo pbocInfo = (PBOCAuthInfo) params;
                                                String pinStr = "执行结果:  "
                                                        + pbocInfo.getResult()
                                                        + "\r\n" + "随机数:  "
                                                        + pbocInfo.getRandom()
                                                        + "\r\n" + "终端交易时间:  "
                                                        + pbocInfo.getTradeTime()
                                                        + "\r\n" + "MAC:  "
                                                        + pbocInfo.getCustMac()
                                                        + "\r\n";
                                                Toast.makeText(mActivity, pinStr,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case ReadECLog:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                ReadECInfo ecLogInfo = (ReadECInfo) params;
                                                String pinStr = "终端序列号:  "
                                                        + ecLogInfo.getTermNo()
                                                        + "\r\n" + "终端交易时间:  "
                                                        + ecLogInfo.getTradeTime()
                                                        + "\r\n" + "读取记录时间:  "
                                                        + ecLogInfo.getReadECTime()
                                                        + "\r\n" + "随机数:  "
                                                        + ecLogInfo.getRandom()
                                                        + "\r\n";
                                                Toast.makeText(mActivity, pinStr,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case DelECLog:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                String delEc = (String) params;
                                                Toast.makeText(mActivity,
                                                        "结果状态码:" + delEc,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case ReadOfflineLog:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                String delEc = (String) params;
                                                Toast.makeText(mActivity,
                                                        "脱机明细统计:" + delEc,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case EndPBOC:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity, "交易成功",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case GetAuthorRandom:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                String delEc = (String) params;
                                                Toast.makeText(mActivity,
                                                        "授权随机数:" + delEc,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case SetOfflineMode:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity, "交易成功",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case Display:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity, "终端显示成功",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case ReadPlainCardNo:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                ReadPlainCardNo readPlainCardNo = (ReadPlainCardNo) params;

                                                Toast.makeText(
                                                        mActivity,
                                                        "终端交易时间:"
                                                                + readPlainCardNo
                                                                .getTradeTime()
                                                                + "\r\n"
                                                                + "卡序号:"
                                                                + readPlainCardNo
                                                                .getCardSeq()
                                                                + "\r\n"
                                                                + "主账号:"
                                                                + readPlainCardNo
                                                                .getCardNo()
                                                                + "\r\n",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    case GetMac:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                String mac = (String) params;
                                                Toast.makeText(mActivity,
                                                        "mac:" + mac,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;
                                    default:
                                        thread = new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method stub
                                                Toast.makeText(mActivity, "终端显示成功",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        };
                                        break;

                                }

                                mActivity.runOnUiThread(thread);

                            }

                            @Override
                            public void onTransFailed(EnumCommand cmd,
                                                      EnumBtErrorCode btcode,
                                                      final int errorCode, final String message) {
                                // TODO Auto-generated method stub
                                Log.e("onTransFailed", message);
                                Runnable thread = null;
                                thread = new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                mActivity,
                                                message
                                                        + "，错误代码:"
                                                        + String.valueOf(errorCode),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                };
                                mActivity.runOnUiThread(thread);
                            }

                        });
                    }

                });
        /************* 获取设备信息 ******************/
        ((Button) findViewById(R.id.btn_shebeijianting))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.getDeviceInfo();
                    }

                });
        /************* 复位 *******************/
        ((Button) findViewById(R.id.button6112))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.reset();
                    }

                });
        /************* gettime *******************/
        ((Button) findViewById(R.id.button0601))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.getTime();
                    }

                });
        /************* settime *******************/
        ((Button) findViewById(R.id.button0602))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.setTime("20150909135530");
                    }

                });
        /************* 读取硬件参数 *******************/
        ((Button) findViewById(R.id.button0603))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.setTime("20150909135530");
                    }

                });

        /************* 设置RSA公钥 *******************/
        ((Button) findViewById(R.id.button0701))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // posApi.setRsaPublicKey("9168C425E0C161F1220BF9586723C3F1C1F7E376F2C0E7EDE0F043122146A292C659E728A666C03BB6194A9D02E03A5B0A0F2FEE476DEC90ACAE88B740133C48515DA08A759D591D484A78E97E292E3B05D76E5EF16863275F28F3A16E21659CDAEA583DBD17F3170045DDBCA977DD004E165603687BE93DE8274C400B0F45B9","65537");
                        posApi.setRsaPublicKey(
                                "df606f65ef102f2afcf964ecd31649938b7eedff3ce9a0d8a71d66ddac66ab31559d3ede9ad122b6ad8ed141ec721171048c5c5ed82ae8552e2cd902e376983d4f4cb60dba186f760fbf023425835ec6eeb313f30ad7b5211dc8e124ad0575de7569b7145e358d8e5e6bcde47ca1b068157705a04ab1549aa126ce296de868ab",
                                "010001");
                    }

                });
        /************* 获取随机密钥密文 *******************/
        ((Button) findViewById(R.id.button0702))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.getRandomKey();
                    }

                });
        /************* 设置主密钥 *******************/
        ((Button) findViewById(R.id.button0703))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.setTMK((byte) 0x01,
                                "12755238614762540685045470232099");
                    }

                });
        /************* 设置工作密钥 *******************/
        ((Button) findViewById(R.id.button0704))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // posApi.setTDK((byte)0x01,
                        // (byte)0x01,"12345678901234561234567890123456",
                        // "00000000",
                        // (byte)0x02,"12345678901234561234567890123456",
                        // "00000000",
                        // (byte)0x03,"12345678901234561234567890123456",
                        // "00000000");
                        posApi.setTDK((byte) 0x01, (byte) 0x01,
                                "EAC526FD6D2908698DAB42F9BD427ABB", "1D859B73",
                                (byte) 0x02,
                                "12345678901234561234567890123456", "733ED91C",
                                (byte) 0x03,
                                "B75099066C9AFF07A395F7B526EF2EF6", "A2B35F39");
                    }

                });

        /************* 验证MAC *******************/
        ((Button) findViewById(R.id.button0801))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        //
                        posApi.vertifyMAC((byte) 0x01, "1234567890123456",
                                "1234567890123456", "9876543210123456");

                    }

                });
        /************* 数据显示确认 *******************/
        ((Button) findViewById(R.id.button0802))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.terminalConfrim((byte) 0x01, (byte) 0x30, "标题",
                                "内容");
                    }

                });
        /********* 打开读卡器 ******************/

        mButton4 = (Button) this.findViewById(R.id.button4);
        mButton4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                posApi.openReader((byte) 0x00, (byte) 0xC0, (byte) 60,
                        (byte) 1, (byte) 2, (byte) 3, "0123456789",
                        "000000000012", "sssss", (byte) 0x00, "userContent",
                        "userEncContent", (byte) 0x00);
            }

        });
        /************* 读取卡号 *******************/
        ((Button) this.findViewById(R.id.button41))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.readCardNo((byte) 60);
                    }

                });

        /************* 读取卡号 *******************/
        ((Button) this.findViewById(R.id.button42))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.readMagCard();
                    }

                });

        /************* 输入密码 *******************/
        ((Button) this.findViewById(R.id.button500))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        byte timeout = 0x10;
                        byte pinMin = 0x04;
                        byte pinMax = 0x0c;
                        String numRandom = "1234567890123456";
                        String account = "000000000011";
                        posApi.getPin(timeout, pinMin, pinMax, numRandom,
                                account);
                    }

                });
        /************* 输入密码 *******************/
        ((Button) this.findViewById(R.id.button501))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        posApi.readPlainCardNo((byte) 0x30);
                    }

                });

        /************* 获取MAC *******************/
        ((Button) this.findViewById(R.id.button502))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        posApi.getMac("12345678901234561234567890123456");
                    }

                });

        mButton3 = (Button) this.findViewById(R.id.button3);
        mButton3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                posApi.updateCA(
                        (byte) 0x04,
                        "9F0605A0000000659F220109DF05083230313431323331DF060101DF070101DF028180B72A8FEF5B27F2B550398FDCC256F714BAD497FF56094B7408328CB626AA6F0E6A9DF8388EB9887BC930170BCC1213E90FC070D52C8DCD0FF9E10FAD36801FE93FC998A721705091F18BC7C98241CADC15A2B9DA7FB963142C0AB640D5D0135E77EBAE95AF1B4FEFADCF9C012366BDDA0455C1564A68810D7127676D493890BDDF040103DF03144410C6D51C2F83ADFD92528FA6E38A32DF048D0A");
            }

        });

        ((Button) this.findViewById(R.id.button8))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        // posApi.updateAID((byte)0x02,
                        // "9F0608A000000333010101DF0101009F09020020DF1105084000A800DF1205D84004F800DF130500100000009F1B0400000001DF150400000000DF160199DF170199DF14039F3704DF180101DF2006000000100000DF2106000000100000DF19060000001000009F7B06000000100000");
                        posApi.updateAID((byte) 0x04, "");
                        // posApi.updateAID((byte)0x03,
                        // "9F0608A000000333010101");
                    }

                });

        ((Button) this.findViewById(R.id.button8111))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.startPBOC("9F02060000000001009F03060000000000009C0100DF7C0101DF710101DF720101DF730100DF74042020060C");
                    }

                });

        ((Button) this.findViewById(R.id.button900))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.startPBOCAuthor("9F0605A0000000659F220109DF05083230313431323331DF060101DF070101DF028180B72A8FEF5B27F2B550398FDCC256F714BAD497FF56094B7408328CB626AA6F0E6A9DF8388EB9887BC930170BCC1213E90FC070D52C8DCD0FF9E10FAD36801FE93FC998A721705091F18BC7C98241CADC15A2B9DA7FB963142C0AB640D5D0135E77EBAE95AF1B4FEFADCF9C012366BDDA0455C1564A68810D7127676D493890BDDF040103DF03144410C6D51C2F83ADFD92528FA6E38A32DF048D0A");
                    }

                });
        ((Button) this.findViewById(R.id.button901))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        short index = 0x0001;
                        posApi.readECLog((byte) 0x00, (byte) 0x00, (byte) 0x00,
                                index);
                    }

                });
        ((Button) this.findViewById(R.id.button902))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        byte type = 0x00;
                        short index = 0x0001;
                        String termNo = "123456789012345678901234";
                        String tradeTime = "20150918140630";
                        posApi.delECLog(type, index, termNo, tradeTime);
                    }

                });

        ((Button) this.findViewById(R.id.button1000))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.readOfflineLog();
                    }

                });

        ((Button) this.findViewById(R.id.button1001))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        byte status = 0x00;
                        posApi.endPBOC(status);
                    }

                });

        ((Button) this.findViewById(R.id.button1002))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        posApi.getAuthorRandom();
                    }

                });
        ((Button) this.findViewById(R.id.button1100))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        byte transNum = 0x10;
                        String random = "1234567890123456";
                        String macdata = "5149C6AF27316341";
                        posApi.setOfflineMode(transNum, random, macdata);
                    }

                });

        mButton11 = (Button) this.findViewById(R.id.button11);
        mButton11.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Thread() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        byte firLineOff = 0x01;
                        String firData = "第一行";
                        byte secLineOff = 0x02;

                        String secData = "第二";
                        byte thiLineOff = 0x03;

                        String thiData = "三";
                        byte timeout = 0x20;

                        posApi.display(firLineOff, firData, secLineOff,
                                secData, thiLineOff, thiData, timeout);
                    }

                }.start();
            }

        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    private String getByteInfo(byte[] byteInfo) {
        String strDisp = "";
        for (int i = 0; i < byteInfo.length; i++) {
            strDisp = strDisp + String.format("%02X", byteInfo[i]);
        }
        Log.e("de", strDisp);
        return strDisp;
    }

    void showDialog(String strTitle, String strMessage) {
        showConfirmDialog(-1, strTitle, strMessage);
    }

    public void showConfirmDialog(final int nConfirmDialogID, String strTitle,
                                  String strMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(strMessage);
        builder.setIcon(R.drawable.ic_launcher).setCancelable(false);
        builder.setTitle(strTitle);
        switch (nConfirmDialogID) {
            case -1:
                builder.setPositiveButton("ȷ��",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                return;
                            }
                        });
                break;

        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.e("onActivityResult",
                String.valueOf(requestCode) + "---"
                        + String.valueOf(resultCode));
        if (requestCode == BluetoothController.INTENT_OPENBT) {
            if (resultCode == RESULT_OK)
                Toast.makeText(mActivity, "蓝牙打开允许", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "蓝牙打开拒绝", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
