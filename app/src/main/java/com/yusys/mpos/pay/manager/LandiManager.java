package com.yusys.mpos.pay.manager;

/**
 * 联迪移动支付终端管理
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-18 17:12
 */
public class LandiManager {

    private LandiManager mInstance;

    private LandiManager() {
    }

    /**
     * 单例
     */
    public LandiManager getInstance() {
        if (mInstance == null) {
            mInstance = new LandiManager();
        }
        return mInstance;
    }
}