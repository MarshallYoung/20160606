package com.yusys.mpos.login.bean;

import com.yusys.mpos.base.bean.YXRequest;

/**
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-11 16:59
 */
public class LoginRequest extends YXRequest {

    /**
     * 手机号
     */
    public String mobilePhoneNumber;
    /**
     * 密码
     */
    public String password;
    /**
     * 手机唯一标志符,uuid
     */
    public String identify;

}