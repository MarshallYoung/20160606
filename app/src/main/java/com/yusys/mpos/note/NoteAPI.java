package com.yusys.mpos.note;

/**
 * 通知的接口
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-02 15:31
 */
public class NoteAPI {
    public interface Mode {
        int SET_PASSWORD = 0x01;// 设置密码
        int REFUND = 0x02;// 信用卡还款
    }
}