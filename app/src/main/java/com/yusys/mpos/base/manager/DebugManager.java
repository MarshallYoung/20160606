package com.yusys.mpos.base.manager;

/**
 * 调试管理者,管理调试信息,或者调试功能
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-05-17 09:37
 */
public class DebugManager {

    /**
     * 全局
     */
    public interface Overall {
        /**
         * 是否打印日志
         */
        boolean LOG_PRINT = true;
    }

    /**
     * 登录
     */
    public interface Login {
        /**
         * 如果开启预览模式,就可以无需验证用户名密码直接进入主界面预览界面样式
         * 0:普通模式,验证登录名密码
         * 1:预览模式,无需验证登录名密码
         */
        boolean LOGIN_MODE_PREVIEW = false;
    }
}