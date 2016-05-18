package com.yusys.mpos.login.bean;

import com.yusys.mpos.base.bean.YXRequest;

/**
 * 退出登录请求
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-11 16:57
 */
public class QuitLoginRequest extends YXRequest {

    /**
     * 手机号
     */
    public String mobilePhoneNumber;
    /**
     * 手机唯一标志符,uuid
     */
    public String identify;

}