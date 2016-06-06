package com.yusys.mpos.register.bean;

import com.yusys.mpos.base.bean.YXRequest;

/**
 * 手机验证码请求
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-11 16:53
 */
public class VerificationCodeRequest extends YXRequest {

    /**
     * 手机号
     */
    public String mobilePhoneNumber;
}