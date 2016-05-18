package com.yusys.mpos.merchant.bean;

import com.yusys.mpos.base.bean.YXRequest;

/**
 * 商户认证
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-11 17:48
 */
public class MerchantApproveRequest extends YXRequest {

    /**
     * 商户名称
     */
    public String merchantName;
    /**
     * 手机号
     */
    public String mobilePhoneNumber;
    /**
     * 身份证号
     */
    public String idCardNumber;
    /**
     * 法人姓名
     */
    public String legalPersonName;
    /**
     * 银行卡号
     */
    public String bankCardNumber;
    /**
     * 银行卡户名
     */
    public String bankAccountName;
    /**
     * 营业地址
     */
    public String businessAddress;
    /**
     * 经营范围
     */
    public String businessScope;
    /**
     * 终端编号
     */
    public String terminalId;

}