package com.yusys.mpos.register.bean;

import com.yusys.mpos.base.bean.YXRequest;

/**
 * 注册请求
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-11 16:31
 */
public class RegisterRequest extends YXRequest {

    /**
     * 手机号
     */
    public String mobilePhoneNumber;
    /**
     * 验证码
     */
    public String verificationCode;
    /**
     * 密码
     */
    public String password;
}